package com.example.welcomeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WelcomeController.class);

    @Autowired
    private GreetFeignClient greetClient;

    @Autowired
    private KafkaProducerService kafkaProducer;

    @GetMapping("/welcome")
    // @org.springframework.cache.annotation.Cacheable(value = "welcome-cache")
    public String welcome(@RequestParam (required = false,defaultValue = "shankar") String name,
                          @RequestParam (required = false,defaultValue = "24") int age,
                          @RequestParam (required = false,defaultValue = "shankar@gmail.com") String email) {
        log.info("Request received at Welcome Service");

        // 1. Call Greet Service (Synchronous)
        String greetResponse = greetClient.getGreet();
        log.info("Received response from Greet Service: {}", greetResponse);

        // 2. Send Event to Kafka (Asynchronous)
        kafkaProducer.sendMessage("user-visits", "User visited Welcome Service at " + java.time.LocalDateTime.now());
        Student student = new Student();
        student.setName(name);
        kafkaProducer.sendStudentKafkaTemplate("student-visits",student);

        return "Welcome to Microservices! (Port: 8081) -> " + greetResponse;
    }
}
