package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StageRequest(
    @NotBlank @Size(
        min = 2,
        max = 50,
        message = "Name must be between 2 and 50 characters long"
    )
    String name) {
    
}
