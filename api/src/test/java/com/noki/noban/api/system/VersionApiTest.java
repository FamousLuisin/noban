package com.noki.noban.api.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.noki.noban.api.exceptions.ExceptionResponse;
import com.noki.noban.api.models.Version;
import com.noki.noban.api.repository.UserRepository;

import io.restassured.http.Header;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.*;

class VersionApiTest extends SystemTestConfig {

    private String versionUrl;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        versionUrl = baseUrl() + "/api/version";
    }

    @Test
    void getVersionWithoutToken() {
        ExceptionResponse response = given()
                .when()
                    .get(versionUrl)
            .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                    .as(ExceptionResponse.class);

        assertNotNull(response);
        assertEquals("/api/version", response.getUri());
        assertEquals("Invalid token", response.getMessage());
    }

    @Test
    void getVersionWithToken(){
        Version version = new Version("noban", "latest");

        Version response = given()
            .header(new Header("Authorization", String.format("Bearer %s", registerUser())))
            .when()
                .get(versionUrl)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                    .as(Version.class);

        assertNotNull(response);
        assertEquals(version.getName(), response.getName());
        assertEquals(version.getVersion(), response.getVersion());
    }
}
