package com.noki.noban.api.dto.response;

import java.util.List;

public record ProjectInvitesResponse(
    List<InviteResponse> invites,
    Integer pageNumber,
    Integer size,
    Boolean hasNext,
    Long totalElements,
    Integer totalPages
) {
    
}
