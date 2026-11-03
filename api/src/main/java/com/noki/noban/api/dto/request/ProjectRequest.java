package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
    @NotBlank(message = "Name is required") 
    @Size(min=5, message = "Name must be at least 5 characters long") 
    String name, 
    @NotBlank(message = "Description is required") 
    @Size(min=5, message = "Description must be at least 5 characters long") 
    String description) {
    
}
