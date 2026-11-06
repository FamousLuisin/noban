package com.noki.noban.api.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noki.noban.api.dto.internal.WelcomeEmail;

@Service 
public class ProducerService {
    
    private final String emailTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerService(
            @Value ("${topics.email.request.topic}") String emailTopic,
            KafkaTemplate<String, Object> kafkaTemplate,
            ObjectMapper objectMapper
        ){
        this.emailTopic = emailTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWelcomeEmail(String to, String name) {
        WelcomeEmail welcomeEmail = new WelcomeEmail(to, name);
        kafkaTemplate.send(emailTopic, welcomeEmail);
    }
}
