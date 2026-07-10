package com.noki.noban.api.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.dto.internal.AuthResult;
import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;
import com.noki.noban.api.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthResult result = authService.register(request);
        
        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
            .maxAge(Duration.ofDays(30))
            .httpOnly(true)
            .secure(false)
            .path("/")
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new JwtResponse(result.accessToken(), result.expiresIn()));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResult result = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
            .maxAge(Duration.ofDays(30))
            .httpOnly(true)
            .secure(false)
            .path("/")
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new JwtResponse(result.accessToken(), result.expiresIn()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@CookieValue(name = "refreshToken", required = true) String token) {
        AuthResult result = authService.refreshToken(token);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
            .maxAge(Duration.ofDays(30))
            .httpOnly(true)
            .secure(false)
            .path("/")
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new JwtResponse(result.accessToken(), result.expiresIn()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = true) String token) {
        
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
            .maxAge(0)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .build();

        return ResponseEntity
            .noContent()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }
    
}
