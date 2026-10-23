package com.noki.noban.api.dto.response;

import java.util.List;

public record StageResponse(
    Integer id, 
    String name, 
    Integer position,
    List<TaskResponse> tasks
) {
    
}
