package com.spring.toogoodtogo.store.controller;

import com.spring.toogoodtogo.confing.CustomUserDetails;
import com.spring.toogoodtogo.store.domain.Store;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    /*
    * POST /api/stores (매장 등록)
    * */
    @PostMapping
    public void save(@AuthenticationPrincipal CustomUserDetails  userDetails) {

    }

    /*
    * GET /api/stores (내 매장 목록 조회)
    * */
    @GetMapping
    public void findStores(){

    }
}
