package com.spring.toogoodtogo.auth.controller;

import com.spring.toogoodtogo.auth.service.AuthService;
import com.spring.toogoodtogo.auth.dto.LoginRequest;
import com.spring.toogoodtogo.auth.dto.LoginResponse;
import com.spring.toogoodtogo.global.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }



    // 로그인 (JWT 발급)
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse response = authService.login(req);
        return ApiResponse.success(HttpStatus.OK.value(), "로그인 성공", response);
    }

}
