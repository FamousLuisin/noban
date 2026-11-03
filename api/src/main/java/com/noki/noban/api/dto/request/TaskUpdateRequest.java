package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskUpdateRequest(@NotBlank String title, 
    @NotBlank String description) {
    
}
