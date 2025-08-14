package com.spring.toogoodtogo.global.exception;

public record CustomErrorResponse(
        String customCode,
        String message
){
}
