package com.spring.toogoodtogo.user.dto;

public record LoginResponse(
        String token, Long userId, String email, String role
) {
}
