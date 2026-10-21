package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskChangeAssignedToRequest(@NotBlank String emailAssignedTo) {
}
