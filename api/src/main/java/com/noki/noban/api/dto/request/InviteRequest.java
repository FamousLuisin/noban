package com.noki.noban.api.dto.request;

import com.noki.noban.api.models.ContributorsModel.RoleType;

import jakarta.validation.constraints.NotBlank;

public record InviteRequest(
    String message,
    RoleType role,
    @NotBlank(message = "Receiver email is required") String receiver
) {
    
}
