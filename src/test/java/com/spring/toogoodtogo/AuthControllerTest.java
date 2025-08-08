package com.spring.toogoodtogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.toogoodtogo.auth.controller.AuthController;
import com.spring.toogoodtogo.auth.dto.LoginRequest;
import com.spring.toogoodtogo.auth.service.AuthService;
import com.spring.toogoodtogo.confing.JwtAuthenticationFilter;
import com.spring.toogoodtogo.confing.JwtUtil;
import com.spring.toogoodtogo.user.controller.UserController;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = {AuthController.class , UserController.class})
@AutoConfigureMockMvc (addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void 회원가입_로그인_성공() throws Exception {
        SignUpRequest request = new SignUpRequest("test@naver.com", "123456", "서동준", "01012345678", "CUSTOMER");
        SignUpResponse response = new SignUpResponse(1L, "test@naver.com", "서동준", "CUSTOMER");

        //UserService.signup(request) 호출되면, 무조건 Response 객체를 반환해라. 특정 상황에 대해 예상되는 동작을 미리 세팅.
        BDDMockito.given(userService.singUp(request)).willReturn(response);

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(request.email()))
                .andExpect(jsonPath("$.data.name").value(request.name()))
                .andExpect(jsonPath("$.code").value(201))
                .andDo(MockMvcResultHandlers.print());

        LoginRequest loginRequest = new LoginRequest("test@naver.com", "123456");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.email").value(loginRequest.email()));

    }

}
