package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserChangeEmailRequest(@NotBlank(message = "Email is required") String email) {
    
}
