package com.noki.noban.api.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;
import com.noki.noban.api.exceptions.ExceptionResponse;
import com.noki.noban.api.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.*;

public class AuthApiTest extends SystemTestConfig {
    
    private String authUrl;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        authUrl = baseUrl() + "/auth";
    }

    @Test
    void registerUserWithValidCredentials(){
        RegisterRequest request = new RegisterRequest("John", "jhonw@email.com", "SenhaForte123!");

        JwtResponse response = given()
            .contentType("application/json")
            .body(request)
            .when()
                .post(authUrl + "/register")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .cookie("refreshToken", notNullValue())
                .extract()
                    .as(JwtResponse.class);

        assertNotNull(response);
        assertNotNull(response.token());
        assertEquals(300000L, response.expiresIn());
    }

    @Test
    void registerUserWithInvalidCredentials(){
        RegisterRequest request = new RegisterRequest("John", "jhonw@email.com", "SenhaForte123");

        ExceptionResponse response = given()
            .contentType("application/json")
            .body(request)
            .when()
                .post(authUrl + "/register")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                    .as(ExceptionResponse.class);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
        assertEquals("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character", response.getMessage());
    }

    @Test
    void loginUserExist(){
        registerUser();

        LoginRequest request = new LoginRequest("jhonw@email.com", "SenhaForte123!");

        JwtResponse response = given()
                .contentType("application/json")
                .body(request)
            .when()
                .post(authUrl + "/login")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .cookie("refreshToken", notNullValue())
                .extract()
                    .as(JwtResponse.class);

        assertNotNull(response);
        assertEquals(300000L, response.expiresIn());
    }

    @Test
    void loginUserNotExist(){
        registerUser();

        LoginRequest request = new LoginRequest("jhonwwww@email.com", "SenhaForte123!");

        ExceptionResponse response = given()
                .contentType("application/json")
                .body(request)
            .when()
                .post(authUrl + "/login")
            .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                    .as(ExceptionResponse.class);

        assertNotNull(response);
        assertEquals(401, response.getStatus());
        assertEquals("Invalid email or password", response.getMessage());
    }
}
