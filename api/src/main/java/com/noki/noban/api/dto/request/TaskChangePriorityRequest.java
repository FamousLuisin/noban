package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.TaskModel.Priority;

import jakarta.validation.constraints.NotNull;

public record TaskChangePriorityRequest(
    @NotNull (message = "Priority is required") Priority priority) {
    
}
