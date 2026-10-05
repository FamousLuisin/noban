package com.noki.noban.api.dto.response;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {
    
}
