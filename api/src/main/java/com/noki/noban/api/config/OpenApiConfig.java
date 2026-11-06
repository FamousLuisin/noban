package com.noki.noban.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI nobanOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Noban API")
                        .description("Documentação da API do Noban"));
    }
}
