package com.noki.noban.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.request.UserChangeEmailRequest;
import com.noki.noban.api.dto.request.UserChangeNameRequest;
import com.noki.noban.api.dto.request.UserChangePasswordRequest;
import com.noki.noban.api.dto.response.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Endpoints relacionados a usuários")
public interface UserControllerDocs {

    @Operation(
        summary = "Obter usuário autenticado",
        description = "Retorna os detalhes do usuário autenticado.")
    ResponseEntity<UserResponse> getCurrentUser();

    @Operation(
        summary = "Alterar e-mail",
        description = "Altera o e-mail do usuário autenticado.")
    ResponseEntity<UserResponse> updateEmail(UserChangeEmailRequest userChangeEmailRequest);

    @Operation(
        summary = "Alterar senha",
        description = "Altera a senha do usuário autenticado.")
    ResponseEntity<UserResponse> updatePassword(UserChangePasswordRequest userChangePasswordRequest);

    @Operation(
        summary = "Alterar nome",
        description = "Altera o nome do usuário autenticado.")
    ResponseEntity<UserResponse> updateName(UserChangeNameRequest userChangeNameRequest);
}
