package com.spring.toogoodtogo;

import com.spring.toogoodtogo.user.controller.UserController;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    // 스프링에서는 MockMvc를 제공하여 컨트롤러단을  테스트 사용
    // MockMvc는 웹 애플리케이션의 컨트롤러를 실행 시키고, 응답 결과를 검증 가능.
    @Autowired
    private MockMvc  mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void 회원가입() throws Exception{
        SignUpRequest request = new SignUpRequest("test1234@naver.com","1122334455","서동준","010-1234-1234","CUSTOMER");
        SignUpResponse response = new SignUpResponse(1L,"test1234@naver.com","서동준","CUSTOMER");

        BDDMockito.given(userService.singUp(BDDMockito.any(SignUpRequest.class))).willReturn(response);

    }
}
