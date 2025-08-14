package com.spring.toogoodtogo.confing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.toogoodtogo.auth.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class ExceptionConfig {

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
        return new CustomAuthenticationEntryPoint(mapper);
    }
}
