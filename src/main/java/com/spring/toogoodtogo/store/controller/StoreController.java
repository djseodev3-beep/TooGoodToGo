package com.spring.toogoodtogo.store.controller;

import com.spring.toogoodtogo.global.ApiResponse;
import com.spring.toogoodtogo.store.dto.CreateStoreRequest;
import com.spring.toogoodtogo.store.dto.StoreResponse;
import com.spring.toogoodtogo.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /*
    * POST /api/stores (매장 등록)
    * 1.PreAutorize를 통해 우선 권한을 검증하고 Service에서는 OwnerID만 받아서 검증할지.
    * 2. 그냥 Service에서 Owner ID를 통해 DB를 통해 데이터를 가져와서 권한과 사용자를 검증할지.
    * */
    @PostMapping
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<ApiResponse<StoreResponse>> save(@AuthenticationPrincipal(expression = "userId") Long ownerId, @RequestBody @Valid CreateStoreRequest request) {
        StoreResponse response = storeService.create(ownerId, request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(HttpStatus.CREATED.value(), "매장 등록 성공", response));

    }

    /*
    * GET /api/stores (내 매장 목록 조회)
    * */
    @GetMapping
    @PreAuthorize("hasRole('STORE_OWNER')")
    public ResponseEntity<ApiResponse<?>> myStores(@AuthenticationPrincipal(expression = "userId") Long userId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(HttpStatus.OK.value(), "사장님 매장 등록 리스트", storeService.findMyStores(userId, pageable)));
    }
}
