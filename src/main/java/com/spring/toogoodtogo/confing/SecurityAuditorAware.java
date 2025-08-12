package com.spring.toogoodtogo.confing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        //예시 : SecurityContext에서 현재 유저 ID 꺼내기
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()
            || auth instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        var principal = (CustomUserDetails) auth.getPrincipal();
        return Optional.of(principal.getUserId());
    }
}
