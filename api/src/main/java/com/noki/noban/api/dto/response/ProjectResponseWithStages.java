package com.noki.noban.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectResponseWithStages(
    UUID id, String name, String description, 
    LocalDateTime createdAt, LocalDateTime updatedAt, 
    UserResponse owner, List<StageResponse> stages) {
    
}
