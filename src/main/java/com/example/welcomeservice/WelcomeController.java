package com.example.welcomeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String welcome() {
        log.info("Request received at Welcome Service");

        // 1. Call Greet Service (Synchronous)
        String greetResponse = greetClient.getGreet();
        log.info("Received response from Greet Service: {}", greetResponse);

        // 2. Send Event to Kafka (Asynchronous)
        kafkaProducer.sendMessage("user-visits", "User visited Welcome Service at " + java.time.LocalDateTime.now());

        return "Welcome to Microservices! (Port: 8081) -> " + greetResponse;
    }
}
