package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskUpdateRequest(
    @NotBlank(message = "Title is required") String title, 
    @NotBlank(message = "Description is required") String description) {
}
