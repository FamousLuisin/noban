package com.noki.noban.api.controller.docs;

import com.noki.noban.api.models.Version;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Versão da API", description = "Endpoints relacionados à versão da API")
public interface VersionControllerDocs {

    @Operation(
        summary = "Consultar versão da API",
        description = "Retorna a versão atual da API.")
    Version getVersion();
}
