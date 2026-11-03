package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.TaskModel.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
    @NotBlank(message = "Title is required") String title, 
    @NotBlank(message = "Description is required") String description, 
    @NotNull(message = "Priority is required") Priority priority, 
    @NotBlank(message = "Email of the user to whom the task is assigned is required") String emailAssignedTo, 
    @NotNull(message = "Stage is required") Integer stage) {
}
