package com.noki.noban.api.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record StageOrderRequest(
    @NotEmpty(message = "Stage IDs are required") List<Integer> stageIds) {
    
}
