package com.spring.toogoodtogo.user.controller;

import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.support.ApiResponse;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.dto.UserInfoResponse;
import com.spring.toogoodtogo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ApiResponse<?> signup(@Valid @RequestBody SignUpRequest request){
        SignUpResponse response = userService.singUp(request);
        return ApiResponse.success(HttpStatus.CREATED.value(), "회원가입 성공", response);
    }

    // JWT에서 userId 추출 후 서비스로 전달 (Spring Security + JwtFilter 필요)
    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // CustomUserDetails는 Spring Security UserDetails 구현체
        UserInfoResponse info = userService.getUserInfo(userDetails.getUserId());
        return ApiResponse.success(HttpStatus.OK.value(), "내 정보 조회 성공", info);
    }
}
