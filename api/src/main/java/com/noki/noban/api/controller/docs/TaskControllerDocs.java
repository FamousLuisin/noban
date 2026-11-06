package com.noki.noban.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.request.TaskChangeAssignedToRequest;
import com.noki.noban.api.dto.request.TaskChangePriorityRequest;
import com.noki.noban.api.dto.request.TaskChangeStageRequest;
import com.noki.noban.api.dto.request.TaskRequest;
import com.noki.noban.api.dto.request.TaskUpdateRequest;
import com.noki.noban.api.dto.response.TaskResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tarefas", description = "Endpoints relacionados a tarefas de projetos")
public interface TaskControllerDocs {

    @Operation(
        summary = "Criar tarefa",
        description = "Cria uma nova tarefa para um projeto específico e retorna os detalhes da tarefa criada.")
    ResponseEntity<TaskResponse> createTask(TaskRequest taskRequest, String projectId);

    @Operation(
        summary = "Mover tarefa para outra etapa",
        description = "Move uma tarefa específica para outra etapa de um projeto.")
    ResponseEntity<TaskResponse> changeStageTask(TaskChangeStageRequest taskRequest, String projectId, String taskId);

    @Operation(
        summary = "Atualizar tarefa",
        description = "Atualiza as informações de uma tarefa específica.")
    ResponseEntity<TaskResponse> updateTask(String taskId, String projectId, TaskUpdateRequest taskUpdateRequest);

    @Operation(
        summary = "Alterar responsável pela tarefa",
        description = "Altera o responsável por uma tarefa específica.")
    ResponseEntity<Void> changeAssignedToTask(String projectId, String taskId, TaskChangeAssignedToRequest taskRequest);

    @Operation(
        summary = "Alterar prioridade da tarefa",
        description = "Altera a prioridade de uma tarefa específica.")
    ResponseEntity<Void> changePriorityTask(String projectId, String taskId, TaskChangePriorityRequest taskRequest);

    @Operation(
        summary = "Excluir tarefa",
        description = "Exclui uma tarefa específica.")
    ResponseEntity<Void> deleteTask(String projectId, String taskId);
}
