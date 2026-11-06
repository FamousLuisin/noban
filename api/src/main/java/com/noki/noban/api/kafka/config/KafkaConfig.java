package com.noki.noban.api.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaConfig {

    private final String emailRequestTopic;

    public KafkaConfig(@Value("${topics.email.request.topic}") String emailRequestTopic) {
        this.emailRequestTopic = emailRequestTopic;
    }

    @Bean 
    public NewTopic topicEmailRequest() {
        return TopicBuilder
            .name(emailRequestTopic)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
