package com.noki.noban.api.system;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import com.noki.noban.api.config.TestContainerConfig;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;

@Import(TestContainerConfig.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class SystemTestConfig {
    
    @LocalServerPort
    protected Integer port;

    protected String baseUrl() {
        return "http://localhost:" + port;
    }

    String registerUser(){
        RestClient restClient = RestClient.create();

        RegisterRequest request = new RegisterRequest("John", "jhonw@email.com", "SenhaForte123!");

        ResponseEntity<JwtResponse> response = restClient.post()
            .uri(baseUrl() + "/auth/register")
            .body(request)
            .retrieve()
            .toEntity(JwtResponse.class);

        return response.getBody().token();
    }
}
