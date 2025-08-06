package com.spring.toogoodtogo.confing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.extractToken(request); // 요청 헤더에서 Bearer 토큰 추출
        if (token != null && jwtUtil.validateToken(token)) { //서명 및 만료 검증
            String email = jwtUtil.getUserEmailFromToken(token);
            UserDetails userDetail = userDetailsService.loadUserByUsername(email); //사용자정보를 DB에서 조회

            //인증객체 생성.
            //UsernamePasswordAuthenticationToken 왜 사용하는가?
            // spring Security 내부에서 사용자를 인증된 상태로 처리하기 위한 인증 객체.
            // JWT 필터에서 인증 완료 처리할 떄 이미 유효한 사용자 정보를 갖고 있기 때문에 패스워드는 null, 인증 완료된 객체를 만들어 SecurityContext에 저장.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            //현재 요청에 인증정보 저장.(이후 @AuthenticationPrincipal로 꺼낼 수 있음.)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response); // 다음 필터로 전달
    }
}
