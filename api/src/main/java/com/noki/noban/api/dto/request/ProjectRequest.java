package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
    @NotBlank @Size(min=5) String name, 
    @NotBlank @Size(min=5) String description) {
    
}
