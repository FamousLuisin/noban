package com.noki.noban.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {
    
    @ServiceConnection
    @Bean
    public PostgreSQLContainer getContainer(){
        return new PostgreSQLContainer("postgres:17-alpine")
            .withDatabaseName("noban")
            .withUsername("admin")
            .withPassword("admin");
    }
}
