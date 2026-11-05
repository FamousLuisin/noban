package com.noki.noban.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.noki.noban.api.dto.request.StageOrderRequest;
import com.noki.noban.api.dto.request.StageRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Etapas", description = "Endpoints relacionados a etapas de projetos")
public interface StageControllerDocs {

    @Operation(
        summary = "Criar etapa",
        description = "Cria uma nova etapa para um projeto específico")
    ResponseEntity<Void> createStage(StageRequest stageRequest, String projectId);

    @Operation(
        summary = "Reordenar etapas",
        description = "Reordena as etapas de um projeto específico")
    ResponseEntity<Void> changeOrderStages(StageOrderRequest stageOrderRequest, String projectId);

    @Operation(
        summary = "Atualizar etapa",
        description = "Atualiza as informações de uma etapa específica de um projeto")
    ResponseEntity<Void> updateStage(String projectId, Integer stageId, StageRequest stageRequest);

    @Operation(
        summary = "Excluir etapa",
        description = "Exclui uma etapa específica de um projeto")
    ResponseEntity<Void> deleteStage(String projectId, Integer stageId);
}
