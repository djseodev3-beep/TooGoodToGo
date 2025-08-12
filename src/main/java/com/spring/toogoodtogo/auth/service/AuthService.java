package com.spring.toogoodtogo.auth.service;

import com.spring.toogoodtogo.auth.dto.LoginRequest;
import com.spring.toogoodtogo.auth.dto.LoginResponse;
import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.confing.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request){
        //Email로 DB에서 사용자 정보를 가져옴, 반환값은 CustomUserDetail.
        //UserDetails을 사용해서 가져오는 이유는 Spring security는 내부적으로 항상 UserDetails 타입을 요구.
        //User Entity에서 바로 검증하지 않는 이유는 추상화/확장성(여러 인증 방법, 권한 체계 등 지원하기 위함)
        //TODO 실패 횟수/계정 잠금 등도 UserDetails 구현에 추가 기능,로그인 성공 시 인증된 principal 객체로 SecurityContext 등록(JWT 인증 필터에서), 실제 서비스라면 "잘못된 아이디"와 "잘못된 비밀번호"를 구분하지 않고 항상 같은 에러 메시지를 주는 게 보안상 안전
    //둘 다 AuthenticationManager에서 AuthenticationProvider에 위임하여 DaoAuthenticationProvider에서 UserDetailService.loadUserbyUsername메서드를 실행 시킨다고함.

        //사용자가 직접 로그인 시, 인증 요청을 만들기 위함.
        //Principal: email, Credentials: password, Authorities: null
        //이 토큰이 AuthenticationManager로 전달되며
        //내부적으로 UserDetailsService + PasswordEncoder가 호출되어 인증됨
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //JWT 토큰 생성 필요.
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponse(token,userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority)
                        .orElse(null));
    }

}
