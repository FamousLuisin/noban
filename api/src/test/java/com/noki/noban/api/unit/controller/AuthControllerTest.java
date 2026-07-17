package com.noki.noban.api.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noki.noban.api.controller.AuthController;
import com.noki.noban.api.dto.internal.AuthResult;
import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;
import com.noki.noban.api.exceptions.ExceptionResponse;
import com.noki.noban.api.repository.UserRepository;
import com.noki.noban.api.security.jwt.JwtService;
import com.noki.noban.api.services.AuthService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {AuthController.class})
public class AuthControllerTest {
    
    @Autowired
    private MockMvcTester mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuthService authService;


    @Test
    void loginShouldReturnJwtResponse() throws Exception{
        LoginRequest loginRequest = new LoginRequest("teste@email", "SenhaForte123/");
        AuthResult authResult = new AuthResult("access", "refresh", 900L);
        JwtResponse jwtResponse = new JwtResponse("access", 900L);

        when(authService.login(any()))
            .thenReturn(authResult);

        MvcTestResult result = mockMvc.post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest))
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.OK)
            .bodyJson()
            .isNotNull()
            .convertTo(JwtResponse.class)
            .isEqualTo(jwtResponse);
    }

    @Test
    void loginWithInvalidCredentials() throws Exception{
        LoginRequest loginRequest = new LoginRequest("teste@email", "");

        MvcTestResult result = mockMvc.post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest))
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.BAD_REQUEST)
            .bodyJson()
            .isNotNull()
            .convertTo(ExceptionResponse.class)
            .satisfies(response -> {
                assertThat(response.getMessage()).isEqualTo("Password is required");
                assertThat(response.getStatus()).isEqualTo(400);
            });
    }

    @Test
    void loginUserNotFound() throws Exception{
        LoginRequest loginRequest = new LoginRequest("teste@email", "SenhaForte123/");

        when(authService.login(any()))
            .thenThrow(BadCredentialsException.class);

        MvcTestResult result = mockMvc.post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest))
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.UNAUTHORIZED)
            .bodyJson()
            .isNotNull()
            .convertTo(ExceptionResponse.class)
            .satisfies(response -> {
                assertThat(response.getMessage()).isEqualTo("Invalid email or password");
                assertThat(response.getStatus()).isEqualTo(401);
            });
    }

    @Test
    void registerShouldReturnJwtResponse() throws Exception{
        AuthResult authResult = new AuthResult("access", "refresh", 900L);
        JwtResponse jwtResponse = new JwtResponse("access", 900L);
        RegisterRequest registerRequest = new RegisterRequest("teste", "teste@email", "SenhaForte123/");

        when(authService.register(registerRequest)).thenReturn(authResult);

        MvcTestResult result = mockMvc.post()
            .uri("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerRequest))
            .exchange();

        result.assertThat()
            .hasStatus(HttpStatus.OK)
            .bodyJson().convertTo(JwtResponse.class)
            .isEqualTo(jwtResponse);
    }

    @Test
    void registerWithInvalidCredentials() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("teste", "teste@email", "SenhaForte123");

        MvcTestResult result = mockMvc.post()
            .uri("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerRequest))
            .exchange();

        result.assertThat()
            .hasStatus(HttpStatus.BAD_REQUEST)
            .bodyJson().convertTo(ExceptionResponse.class)
            .satisfies(response -> {
                assertThat(response.getMessage()).isEqualTo("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
                assertThat(response.getStatus()).isEqualTo(400);
            });
    }

    @Test
    void refreshShouldReturnJwtResponse(){
        AuthResult authResult = new AuthResult("access", "refresh", 900L);
        JwtResponse jwtResponse = new JwtResponse("access", 900L);
        
        when(authService.refreshToken(any())).thenReturn(authResult);

        Cookie cookie = new Cookie("refreshToken", "refresh");

        MvcTestResult result = mockMvc.get()
            .uri("/auth/refresh")
            .cookie(cookie)
            .exchange();

        result.assertThat()
            .hasStatus(HttpStatus.OK)
            .bodyJson().convertTo(JwtResponse.class)
            .isEqualTo(jwtResponse);
            
        Cookie returned = result.getResponse().getCookie("refreshToken");

        assertThat(returned).isNotNull();
        assertThat(returned.getValue()).isEqualTo("refresh");
        assertThat(returned.isHttpOnly()).isTrue();
        assertThat(returned.getPath()).isEqualTo("/");
    }

    @Test
    void refreshWithTokenExpired(){
        when(authService.refreshToken(any()))
            .thenThrow(ExpiredJwtException.class);

        Cookie cookie = new Cookie("refreshToken", "refresh");

        MvcTestResult result = mockMvc.get()
            .uri("/auth/refresh")
            .cookie(cookie)
            .exchange();

        result.assertThat()
            .hasStatus(HttpStatus.UNAUTHORIZED)
            .bodyJson().convertTo(ExceptionResponse.class)
            .satisfies(response -> {
                assertThat(response.getStatus()).isEqualTo(401);
                assertThat(response.getMessage()).isEqualTo("Token has expired");
            });
    }

    @Test
    void logoutShouldClearRefreshTokenCookie() {
        Cookie cookie = new Cookie("refreshToken", "refresh");

        MvcTestResult result = mockMvc.get()
            .uri("/auth/logout")
            .cookie(cookie)
            .exchange();

        result.assertThat()
            .hasStatus(HttpStatus.NO_CONTENT);

        String setCookie = result.getResponse().getHeader(HttpHeaders.SET_COOKIE);

        assertThat(setCookie).isNotNull();
        assertThat(setCookie).contains("refreshToken=");
        assertThat(setCookie).contains("Max-Age=0");
        assertThat(setCookie).contains("HttpOnly");
        assertThat(setCookie).contains("Path=/");
    }
}
