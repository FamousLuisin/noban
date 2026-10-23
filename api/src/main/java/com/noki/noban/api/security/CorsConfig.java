package com.noki.noban.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    
    private final List<String> origin;
    private final List<String> header;
    private final List<String> method;

    public CorsConfig(
        @Value("${cors.origin}") List<String> origin,
        @Value("${cors.header}") List<String> header,
        @Value("${cors.method}") List<String> method
    ){
        this.origin = origin;
        this.header = header;
        this.method = method;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(origin);
        config.setAllowedHeaders(header);;
        config.setAllowedMethods(method);;
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
