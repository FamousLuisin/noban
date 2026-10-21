package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.TaskModel.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskUpdateRequest(@NotBlank String title, 
    @NotBlank String description, 
    @NotNull Priority priority) {
    
}
