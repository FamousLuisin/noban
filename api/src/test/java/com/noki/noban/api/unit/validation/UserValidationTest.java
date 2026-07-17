package com.noki.noban.api.unit.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class UserValidationTest {
    
    private Validator validator;

    @BeforeEach
    void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void loginRequestValid(){
        LoginRequest loginRequest = new LoginRequest("teste@email", "Senha123!");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void loginRequestInvalidEmail(){
        LoginRequest loginRequest = new LoginRequest("teste", "Senha123!");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertFalse(violations.isEmpty());
        assertThat(violations, Matchers.hasSize(1));
        assertThat(violations.stream().map(ConstraintViolation::getMessage).toList(), Matchers.contains("Email should be valid"));
    }

    @Test
    public void registerRequestValid(){
        RegisterRequest registerRequest = new RegisterRequest("teste", "teste@email", "Senha123!");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void registerRequestInvalidPassword(){
        RegisterRequest registerRequest = new RegisterRequest("teste", "teste@email", "Senha123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertFalse(violations.isEmpty());
        assertThat(violations, Matchers.hasSize(1));
        assertThat(violations
            .stream()
            .map(ConstraintViolation::getMessage)
            .toList(), Matchers.contains("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character"));
    }

    @Test
    public void registerRequestInvalidName(){
        RegisterRequest registerRequest = new RegisterRequest("", "teste@email", "Senha123!");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertFalse(violations.isEmpty());
        assertThat(violations, Matchers.hasSize(2));
        assertThat(violations
            .stream()
            .map(ConstraintViolation::getMessage)
            .toList(), Matchers.containsInAnyOrder("Name must be between 2 and 50 characters", "Name is required"));
    }
}
