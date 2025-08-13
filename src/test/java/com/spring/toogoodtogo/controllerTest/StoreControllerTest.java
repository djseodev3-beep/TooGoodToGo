package com.spring.toogoodtogo.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.confing.JwtAuthenticationFilter;
import com.spring.toogoodtogo.confing.JwtUtil;
import com.spring.toogoodtogo.store.controller.StoreController;
import com.spring.toogoodtogo.store.domain.Store;
import com.spring.toogoodtogo.store.dto.CreateStoreRequest;
import com.spring.toogoodtogo.store.dto.StoreResponse;
import com.spring.toogoodtogo.store.service.StoreService;
import com.spring.toogoodtogo.user.domain.User;
import com.spring.toogoodtogo.user.domain.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalTime;

import static javax.management.Query.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StoreService storeService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtAuthenticationFilter authenticationFilter;

    private CustomUserDetails ownerPrincipal(Long id) {
        com.spring.toogoodtogo.user.domain.User u = User.builder()
                .id(id)
                .email("owner@togo.com")
                .password("pw")
                .role(UserRole.STORE_OWNER)
                .build();
        return new CustomUserDetails(u);
    }

    @Test
    @DisplayName("매장 등록 성공 → 201 Created & data.storeId 존재")
    void createStore_success_201() throws Exception {
        // given
        CreateStoreRequest request = new CreateStoreRequest();
        request.setName("test");
        request.setAddress("인천");
        request.setPhone("01012345678");
        request.setDescription("테스트 매장");
        request.setOpenTime(LocalTime.parse("09:00"));
        request.setCloseTime(LocalTime.parse("11:00"));
        request.setLatitude(new BigDecimal("37.5111111"));
        request.setLongitude(new BigDecimal("127.5111111"));

        com.spring.toogoodtogo.user.domain.User u = User.builder()
                .id(1L)
                .email("owner@togo.com")
                .password("pw")
                .role(UserRole.STORE_OWNER)
                .build();

        Store store =Store.builder()
                .ownerId(u)
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        mockMvc.perform(post("/api/stores")
                        .with(user(ownerPrincipal(store.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.storeId").value(store.getId()));
    }


}
