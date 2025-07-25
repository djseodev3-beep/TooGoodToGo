package com.spring.toogoodtogo.user.controller;

import com.spring.toogoodtogo.support.ApiResponse;
import com.spring.toogoodtogo.user.domain.User;
import com.spring.toogoodtogo.user.dto.LoginRequest;
import com.spring.toogoodtogo.user.dto.LoginResponse;
import com.spring.toogoodtogo.user.service.AuthService;
import com.spring.toogoodtogo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }



    // 로그인 (JWT 발급)
    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest req) {

/*        User user = userService.authenticate(req.getEmail(), req.getPassword());
        if (user == null) {
            return ApiResponse.<LoginResponse>builder()
                    .success(false).code(401)
                    .message("이메일 또는 비밀번호가 올바르지 않습니다.")
                    .timestamp(java.time.ZonedDateTime.now().toString())
                    .build();
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        LoginResponse loginRes = new LoginResponse(token, user.getId(), user.getEmail(), user.getRole().name());
        return ApiResponse.<LoginResponse>builder()
                .success(true).code(200)
                .message("로그인 성공")
                .data(loginRes)
                .timestamp(java.time.ZonedDateTime.now().toString())
                .build();
    */}
}
