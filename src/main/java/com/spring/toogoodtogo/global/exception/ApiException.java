package com.spring.toogoodtogo.global.exception;

public class ApiException extends RuntimeException {

    private final ApiErrorCode errorCode;

    public ApiException(ApiErrorCode errorCode) {
        super(errorCode.customCode() + ": " + errorCode.message());
        this.errorCode = errorCode;
    }

    public ApiErrorCode apiErrorCode() {
        return errorCode;
    }
}