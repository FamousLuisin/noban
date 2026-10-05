package com.noki.noban.api.dto.response;

import java.util.List;

public record UserProjectsResponse(
    UserResponse user, 
    List<ProjectResponse> listProjects, 
    Integer page, 
    Integer size, 
    Boolean hasNext, 
    Long totalElements,
    Integer totalPages
) {
    
}
