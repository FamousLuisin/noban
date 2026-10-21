package com.noki.noban.api.dto.response;

import java.util.UUID;

public record TaskResponse(UUID id, String title, String description, String priority, String createdBy, String assignedTo) {
    
}
