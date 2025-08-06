package com.spring.toogoodtogo.global;


import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;   // 성공/실패 여부
    private int code;          // HTTP 상태코드 또는 비즈니스 코드
    private String message;    // 메시지(에러/성공)
    private T data;            // 실제 데이터
    private String timestamp;  // 응답 생성 시각

    // 팩토리 메서드(권장)
    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(code)
                .message(message)
                .data(data)
                .timestamp(ZonedDateTime.now().toString())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .data(null)
                .timestamp(ZonedDateTime.now().toString())
                .build();
    }
}