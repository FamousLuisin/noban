package com.noki.noban.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Name is required") 
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    String name, 
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid") 
    String email, 
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.{8,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$", 
             message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    String password) {
    
}
