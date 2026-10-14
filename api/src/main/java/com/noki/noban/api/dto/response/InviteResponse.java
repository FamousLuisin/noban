package com.noki.noban.api.dto.response;

import java.time.LocalDateTime;

public record InviteResponse(
    Integer id,
    String project,
    String message,
    String receiver,
    String sender,
    String role,
    LocalDateTime sentAt,
    String status
) {
    
}
