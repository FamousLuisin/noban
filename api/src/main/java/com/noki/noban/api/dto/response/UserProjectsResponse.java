package com.noki.noban.api.dto.response;

import java.util.List;

import com.noki.noban.api.dto.projection.ProjectMembership;

public record UserProjectsResponse(
    UserResponse user, 
    List<ProjectMembership> listProjects, 
    Integer page, 
    Integer size, 
    Boolean hasNext, 
    Long totalElements,
    Integer totalPages
) {
    
}
