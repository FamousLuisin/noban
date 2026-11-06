package com.noki.noban.api.controller.docs;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.response.UserInvitesResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Convites", description = "Endpoints relacionados a convites de projetos")
public interface InviteControllerDocs {

    @Operation(
        summary = "Listar meus convites",
        description = "Retorna uma lista paginada de convites recebidos pelo usuário autenticado. O tamanho da página deve ser entre 1 e 100, e a página deve ser maior ou igual a 0.")
    ResponseEntity<UserInvitesResponse> getMyInvites(Integer size, Integer page, Direction direction);

    @Operation(
        summary = "Aceitar convite",
        description = "Aceita um convite recebido pelo usuário autenticado.")
    ResponseEntity<Void> acceptInvite(Integer inviteId);

    @Operation(
        summary = "Recusar convite",
        description = "Recusa um convite recebido pelo usuário autenticado.")
    ResponseEntity<Void> declineInvite(Integer inviteId);

    @Operation(
        summary = "Excluir convite",
        description = "Exclui um convite recebido pelo usuário autenticado.")
    ResponseEntity<Void> deleteInvite(Integer inviteId);
}
