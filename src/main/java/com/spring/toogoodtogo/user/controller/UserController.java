package com.spring.toogoodtogo.user.controller;

import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.global.ApiResponse;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.dto.UserInfoResponse;
import com.spring.toogoodtogo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

/*
*Controller (프레젠테이션 계층, Web Layer)
클라이언트의 요청(Request) → 비즈니스 처리 → 응답(Response)
REST API라면 URL, HTTP 메서드 등 요청/응답 담당
예) 파라미터 검증, DTO 변환, 응답 포맷 통일, 예외 핸들링 등
비즈니스 로직/DB 접근/권한 검사 직접 구현 X (딱 “입구”만 담당)
* */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody SignUpRequest request){
        SignUpResponse response = userService.singUp(request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(HttpStatus.CREATED.value(), "회원가입 성공", response));
    }

    // JWT에서 userId 추출 후 서비스로 전달 (Spring Security + JwtFilter 필요)
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // CustomUserDetails는 Spring Security UserDetails 구현체
        UserInfoResponse info = userService.getUserInfo(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(HttpStatus.OK.value(), "내 정보 조회 성공", info));
    }
}
