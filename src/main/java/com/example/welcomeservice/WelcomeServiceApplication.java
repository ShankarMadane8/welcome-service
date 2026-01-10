package com.example.welcomeservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@org.springframework.cache.annotation.EnableCaching
public class WelcomeServiceApplication {

    @Value("${welcome.msg}")
    String message;
    public static void main(String[] args) {
        SpringApplication.run(WelcomeServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.err.println(message);
        System.err.println("Welcome Service Application started successfully.");
    }

}
