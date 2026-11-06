package com.noki.noban.api.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.internal.WelcomeEmail;
import com.noki.noban.api.mail.EmailService;

@Service 
public class ConsumerService {

    private final EmailService emailService;

    public ConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${topics.email.request.topic}", containerFactory = "welcomeEmailListenerContainerFactory", groupId = "email-consumer-group")
    public void consumeWelcomeEmail(WelcomeEmail welcomeEmail) {
        emailService.sendEmailWelcome(welcomeEmail.subject(), welcomeEmail.name());
    }
}
