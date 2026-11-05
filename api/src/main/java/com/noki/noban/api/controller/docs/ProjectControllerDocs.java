package com.noki.noban.api.controller.docs;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.request.InviteRequest;
import com.noki.noban.api.dto.request.ProjectRequest;
import com.noki.noban.api.dto.response.ProjectInvitesResponse;
import com.noki.noban.api.dto.response.ProjectResponse;
import com.noki.noban.api.dto.response.ProjectResponseWithStages;
import com.noki.noban.api.dto.response.ProjectUsersResponse;
import com.noki.noban.api.dto.response.UserProjectsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Projetos", description = "Endpoints relacionados a projetos")
public interface ProjectControllerDocs {

    @Operation(
        summary = "Criar projeto",
        description = "Cria um novo projeto para o usuário autenticado e retorna os detalhes do projeto criado, incluindo seus estágios.")
    ResponseEntity<ProjectResponseWithStages> createProject(ProjectRequest request);

    @Operation(
        summary = "Listar meus projetos",
        description = "Retorna uma lista paginada de projetos criados pelo usuário autenticado. O tamanho da página deve ser entre 1 e 100, e a página deve ser maior ou igual a 0.")
    ResponseEntity<UserProjectsResponse> listMyProjects(Integer size, Integer page, Direction direction);

    @Operation(
        summary = "Listar usuários de um projeto",
        description = "Retorna uma lista paginada de usuários associados a um projeto específico. O tamanho da página deve ser entre 1 e 100, e a página deve ser maior ou igual a 0.")
    ResponseEntity<ProjectUsersResponse> listProjectUsers(String id, Integer size, Integer page, Direction direction);

    @Operation(
        summary = "Obter projeto",
        description = "Retorna os detalhes de um projeto específico, incluindo seus estágios.")
    ResponseEntity<ProjectResponseWithStages> getProject(String id);

    @Operation(
        summary = "Atualizar projeto",
        description = "Atualiza as informações de um projeto específico.")
    ResponseEntity<ProjectResponse> updateProject(String id, ProjectRequest request);

    @Operation(
        summary = "Excluir projeto",
        description = "Exclui um projeto específico.")
    ResponseEntity<Void> deleteProject(String id);

    @Operation(
        summary = "Convidar usuário para o projeto",
        description = "Envia um convite para um usuário específico para se juntar a um projeto.")
    ResponseEntity<Void> createInvite(InviteRequest inviteRequest, String projectId);

    @Operation(
        summary = "Listar convites do projeto",
        description = "Retorna uma lista paginada de convites associados a um projeto específico. O tamanho da página deve ser entre 1 e 100, e a página deve ser maior ou igual a 0.")
    ResponseEntity<ProjectInvitesResponse> listProjectInvites(String projectId, Integer size, Integer page, Direction direction);
}
