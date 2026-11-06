package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TaskChangeStageRequest(
    @NotNull(message = "Stage ID is required") 
    @Min(value = 1, message = "Stage ID must be greater than or equal to 1")
    Integer stageId) {
    
}
