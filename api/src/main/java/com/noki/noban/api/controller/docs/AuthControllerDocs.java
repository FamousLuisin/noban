package com.noki.noban.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação de usuários")
public interface AuthControllerDocs {

    @Operation(
        summary = "Registrar usuário", 
        description = "Cria uma conta e retorna um token de acesso. O refresh token é enviado em cookie HTTP-only.")
    ResponseEntity<JwtResponse> register(RegisterRequest request);

    @Operation(
        summary = "Autenticar usuário", 
        description = "Autentica as credenciais e retorna um token de acesso. O refresh token é enviado em cookie HTTP-only.")
    ResponseEntity<JwtResponse> login(LoginRequest request);

    @Operation(
        summary = "Atualizar tokens", 
        description = "Usa o cookie refreshToken para emitir novos tokens.")
    ResponseEntity<JwtResponse> refreshToken(String token);

    @Operation(
        summary = "Encerrar sessão", 
        description = "Remove o cookie refreshToken do cliente.")
    ResponseEntity<?> logout(String token);
}
