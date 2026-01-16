package com.example.welcomeservice.service;

import com.example.welcomeservice.dto.Student;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, Student> studentKafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Student> studentKafkaTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.studentKafkaTemplate = studentKafkaTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(String topic, String message) {
        String key = java.util.UUID.randomUUID().toString();
        log.info("Sending message to Kafka topic '{}' with key '{}': {}", topic, key, message);
        kafkaTemplate.send(topic, key, message);
    }
    public void sendStudentKafkaTemplate(String topic, Student student) {
        String key = java.util.UUID.randomUUID().toString();
        log.info("Sending sendStudentKafkaTemplate to Kafka topic '{}' with key '{}': {}", topic, key, student);
        studentKafkaTemplate.send(topic, key, student);
    }
}
