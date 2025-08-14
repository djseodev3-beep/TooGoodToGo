package com.spring.toogoodtogo.store.service;

import com.spring.toogoodtogo.global.exception.ApiErrorCode;
import com.spring.toogoodtogo.global.exception.ApiException;
import com.spring.toogoodtogo.global.exception.GlobalErrorCode;
import com.spring.toogoodtogo.store.domain.Store;
import com.spring.toogoodtogo.store.dto.CreateStoreRequest;
import com.spring.toogoodtogo.store.dto.StoreResponse;
import com.spring.toogoodtogo.store.repository.StoreRepository;
import com.spring.toogoodtogo.user.domain.User;
import com.spring.toogoodtogo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreResponse create(Long id, CreateStoreRequest request) {
        User owner = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        assertOwnerActive(owner);

        if(storeRepository.existsByOwnerIdAndName(owner,request.getName())) {
            throw new IllegalArgumentException("Store already exists");
        }

        Store store =Store.builder()
                .ownerId(owner)
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Store saved = storeRepository.save(store);
        return StoreResponse.from(saved);
    }

    public Page<StoreResponse> getStores(Pageable pageable, String q) {
        Page<Store> list = (q == null || q.isBlank())
                ? storeRepository.findAll(pageable)
                : storeRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(q,pageable);
        return list.map(StoreResponse::from);
    }

    public Page<StoreResponse> getStoresByOwnerId(Long ownerId, Pageable pageable) {
        return storeRepository.findByOwnerId(ownerId, pageable).map(StoreResponse::from);
    }

    public StoreResponse getStoresByStoreId(Long storeId) {
        return StoreResponse.from(storeRepository.findById(storeId).orElseThrow(()-> new ApiException(GlobalErrorCode.NOT_FOUND_STORE)));
    }



    private void assertOwnerActive(User owner){
        if (!owner.isStoreOwner() || !owner.isEnabled()) {
            throw new IllegalArgumentException("사장님만 등록 가능(또는 비활성화 계정)");
        }
    }
}
