package com.spring.toogoodtogo.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.toogoodtogo.global.exception.CustomErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;


@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AuthApiErrorCode errorCode = AuthApiErrorCode.TOKEN_INVALID;

        response.setStatus(errorCode.httpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        CustomErrorResponse errorResponse = new CustomErrorResponse(errorCode.customCode(),errorCode.message());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
