package com.spring.toogoodtogo.user.service;

import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.confing.JwtUtil;
import com.spring.toogoodtogo.user.dto.LoginRequest;
import com.spring.toogoodtogo.user.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request){
        //Email로 DB에서 사용자 정보를 가져옴, 반환값은 CustomUserDetail.
        //UserDetails을 사용해서 가져오는 이유는 Spring security는 내부적으로 항상 UserDetails 타입을 요구.
        //User Entity에서 바로 검증하지 않는 이유는 추상화/확장성(여러 인증 방법, 권한 체계 등 지원하기 위함)
        //TODO 실패 횟수/계정 잠금 등도 UserDetails 구현에 추가 기능,로그인 성공 시 인증된 principal 객체로 SecurityContext 등록(JWT 인증 필터에서), 실제 서비스라면 "잘못된 아이디"와 "잘못된 비밀번호"를 구분하지 않고 항상 같은 에러 메시지를 주는 게 보안상 안전
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.email());

        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        //JWT 토큰 생성 필요.
        String token = new JwtUtil().generateToken(userDetails);
        return new LoginResponse(token,userDetails.getUserId(),userDetails.getUsername(),userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse(null));

    }

}
