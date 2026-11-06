package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.ContributorsModel.RoleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteRequest(
    String message,
    @NotNull(message = "Role is required") 
    RoleType role,
    @NotBlank(message = "Receiver email is required")
    @Email(message = "Receiver email must be a valid email address")
    String receiver
) {
    
}
