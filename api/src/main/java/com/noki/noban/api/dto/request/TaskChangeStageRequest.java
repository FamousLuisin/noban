package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskChangeStageRequest(
    @NotNull (message = "Stage ID is required") Integer stageId) {
    
}
