package com.spring.toogoodtogo.user.dto;

public record UserInfoResponse(
        Long userId, String email, String name, String phone, String role) {
}
