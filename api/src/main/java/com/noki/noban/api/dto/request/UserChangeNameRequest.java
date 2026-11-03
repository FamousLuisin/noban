package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserChangeNameRequest(@NotBlank(message = "Name is required") String name) {
    
}
