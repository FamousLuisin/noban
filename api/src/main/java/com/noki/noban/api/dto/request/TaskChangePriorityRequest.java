package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.TaskModel.Priority;

import jakarta.validation.constraints.NotBlank;

public record TaskChangePriorityRequest(@NotBlank  Priority priority) {
    
}
