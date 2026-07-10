package com.noki.noban.api.dto.internal;

public record AuthResult(String accessToken, String refreshToken, Long expiresIn) {
    
}
