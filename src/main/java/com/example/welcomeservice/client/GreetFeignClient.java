package com.example.welcomeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "greet-service")
public interface GreetFeignClient {

    @GetMapping("/greet")
    public String getGreet();
}
