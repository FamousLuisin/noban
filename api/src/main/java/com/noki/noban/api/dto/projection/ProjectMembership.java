package com.noki.noban.api.dto.projection;

import java.time.LocalDateTime;
import java.util.UUID;

import com.noki.noban.api.dto.response.UserResponse;
import com.noki.noban.api.models.ContributorsModel.RoleType;

public record ProjectMembership(UUID id, 
        String name, 
        String description, 
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserResponse owner,
        RoleType role,
        LocalDateTime joinedAt
    ) {
    
}
