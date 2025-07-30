package com.spring.toogoodtogo.support.exceptionm;

import com.spring.toogoodtogo.support.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//스프링에서 전역 예외처리기 역활을 하는 클래스에 붙임
// 프로젝트 내 @RestController에서 발생한 예외를 가로채서 공통 응답/에러 포맷(ApiResponse)으로 변환.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //@ExceptionHandler는 해당 메서드가 특정 예외 타입이 발생했을 때 호출 됨.
    //@Valid @RequestBody 등의 Validation에서 값이 잘못 들어온 경우 검증 실패한 필드와 메시지를 모아서 400와 함께 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("요청 값이 올바르지 않습니다.");
        return ApiResponse.error(400, message);
    }

    //IllegalArgumentException 관련
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleIllegalExceptions(IllegalArgumentException e) {
        return ApiResponse.error(401, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(500, "Internal Server Error");
    }
}
