package com.noki.noban.api.dto.response;

import java.util.List;

import com.noki.noban.api.dto.projection.UserSummaryProject;

public record ProjectUsersResponse(
    ProjectResponse project,
    List<UserSummaryProject> users,
    Integer pageNumber,
    Integer size,
    Boolean hasNext,
    Long totalElements,
    Integer totalPages
) {
    
}
