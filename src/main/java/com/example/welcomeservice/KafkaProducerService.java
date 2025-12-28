package com.example.welcomeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        String key = java.util.UUID.randomUUID().toString();
        log.info("Sending message to Kafka topic '{}' with key '{}': {}", topic, key, message);
        kafkaTemplate.send(topic, key, message);
    }
}
