package com.spring.toogoodtogo.user.dto;

public record SignUpResponse(
        Long id, String email, String name, String role
) {
}
