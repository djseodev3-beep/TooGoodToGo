package com.spring.toogoodtogo.global.exception;

public interface ApiErrorCode {

    int httpStatus();

    String customCode();

    String message();
}
