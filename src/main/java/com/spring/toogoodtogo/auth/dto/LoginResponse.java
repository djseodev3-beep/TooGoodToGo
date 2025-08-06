package com.spring.toogoodtogo.auth.dto;

public record LoginResponse(
        String token, Long userId, String email, String role
) {
}
