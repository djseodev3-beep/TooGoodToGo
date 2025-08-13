package com.spring.toogoodtogo.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.toogoodtogo.auth.controller.AuthController;
import com.spring.toogoodtogo.auth.dto.LoginRequest;
import com.spring.toogoodtogo.auth.service.AuthService;
import com.spring.toogoodtogo.confing.JwtAuthenticationFilter;
import com.spring.toogoodtogo.confing.SecurityConfig;
import com.spring.toogoodtogo.user.controller.UserController;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = {AuthController.class , UserController.class})
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @BeforeEach
    void passThroughJwt() throws Exception {
        Mockito.doAnswer(inv -> {
            var req = inv.getArgument(0);
            var res = inv.getArgument(1);
            var chain = (FilterChain) inv.getArgument(2);
            chain.doFilter((ServletRequest) req, (ServletResponse) res);
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(),any(),any());
    }


    @Test
    void 회원가입_로그인_성공() throws Exception {
        SignUpRequest request = new SignUpRequest("test@naver.com", "123456", "서동준", "01012345678", "CUSTOMER");
        SignUpResponse response = new SignUpResponse(1L, "test@naver.com", "서동준", "CUSTOMER");

        //UserService.signup(request) 호출되면, 무조건 Response 객체를 반환해라. 특정 상황에 대해 예상되는 동작을 미리 세팅.
        BDDMockito.given(userService.singUp(request)).willReturn(response);

        mockMvc.perform(post("/api/users/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(request.email()))
                .andExpect(jsonPath("$.data.name").value(request.name()))
                .andExpect(jsonPath("$.code").value(201));

        LoginRequest loginRequest = new LoginRequest("test@naver.com", "123456");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.email").value(loginRequest.email()));

    }

}
