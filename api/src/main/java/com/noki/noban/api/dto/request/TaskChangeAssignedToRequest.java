package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TaskChangeAssignedToRequest(
    @NotBlank(message = "Email of the user to whom the task is assigned is required")
    @Email(message = "Email of the user to whom the task is assigned must be a valid email address")
    String emailAssignedTo) {
}
