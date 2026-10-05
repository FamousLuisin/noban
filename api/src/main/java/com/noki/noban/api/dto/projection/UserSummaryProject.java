package com.noki.noban.api.dto.projection;

import java.util.UUID;

import com.noki.noban.api.models.ContributorsModel.RoleType;

public record UserSummaryProject(
    UUID id,
    String name,
    String email,
    RoleType role
) {
    
}
