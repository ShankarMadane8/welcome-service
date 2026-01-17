package com.example.welcomeservice.controller;

import com.example.welcomeservice.client.GreetFeignClient;
import com.example.welcomeservice.dto.Student;
import com.example.welcomeservice.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Welcome", description = "Welcome service operations with Kafka integration")
public class WelcomeController {


    @Autowired
    private GreetFeignClient greetClient;

    @Autowired
    private KafkaProducerService kafkaProducer;

    @Value("${server.port}")
    private String port;

    @Operation(summary = "Welcome endpoint with student creation", description = "This endpoint orchestrates multiple operations: "
            +
            "1) Calls the Greet Service synchronously via Feign, " +
            "2) Publishes a user visit event to Kafka, " +
            "3) Creates a student record with PENDING status and publishes to Kafka for async processing. " +
            "The student will be consumed by Greet Service and processed by the batch scheduler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed welcome request")
    })
    @GetMapping("/welcome")
//  @Cacheable(value = "welcome-cache")
    public String welcome(
            @Parameter(description = "Name of the student", example = "shankar") @RequestParam(required = false, defaultValue = "shankar") String name,
            @Parameter(description = "Age of the student", example = "24") @RequestParam(required = false, defaultValue = "24") int age,
            @Parameter(description = "Email address of the student", example = "shankar@gmail.com") @RequestParam(required = false, defaultValue = "shankar@gmail.com") String email) {
        log.info("Request received at Welcome Service");

        // 1. Call Greet Service (Synchronous)
        String greetResponse = greetClient.getGreet();
        log.info("Received response from Greet Service: {}", greetResponse);

        // 2. Send Event to Kafka (Asynchronous)
        kafkaProducer.sendMessage("user-visits", "User visited Welcome Service at " + java.time.LocalDateTime.now());
        Student student = Student.builder()
                .name(name)
                .age(age)
                .email(email)
                .address("Bangalore") // Default/Dummy address
                .course("Microservices") // Default/Dummy course
                .status("PENDING")
                .build();
        kafkaProducer.sendStudentKafkaTemplate("student-visits", student);

        return "Welcome to Microservices! (Port: "+port+") -> " + greetResponse;
    }
}
