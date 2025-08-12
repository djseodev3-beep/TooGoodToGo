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

    @Autowired
    private MockMvc  mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void 회원가입() throws Exception{


    }
}
