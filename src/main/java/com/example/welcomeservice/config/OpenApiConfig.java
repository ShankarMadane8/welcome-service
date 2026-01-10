package com.example.welcomeservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Value("${server.port:8081}")
        private String serverPort;

        @Bean
        public OpenAPI welcomeServiceOpenAPI() {
                Server localServer = new Server();
                localServer.setUrl("http://localhost:" + serverPort);
                localServer.setDescription("Local Development Server");

                Server gatewayServer = new Server();
                gatewayServer.setUrl("http://localhost:8082/welcome-service");
                gatewayServer.setDescription("API Gateway Server");

                Contact contact = new Contact();
                contact.setEmail("support@microservices.com");
                contact.setName("Microservices Team");
                contact.setUrl("https://www.microservices.com");

                License mitLicense = new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/");

                Info info = new Info()
                                .title("Welcome Service API")
                                .version("1.0.0")
                                .contact(contact)
                                .description("This API serves as the entry point for the microservices ecosystem. " +
                                                "It orchestrates calls to downstream services, publishes events to Kafka, "
                                                +
                                                "and provides welcome functionality with student creation capabilities.")
                                .termsOfService("https://www.microservices.com/terms")
                                .license(mitLicense);

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(localServer, gatewayServer));
        }
}
