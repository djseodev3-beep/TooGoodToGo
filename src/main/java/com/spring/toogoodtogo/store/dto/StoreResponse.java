package com.spring.toogoodtogo.store.dto;

import com.spring.toogoodtogo.store.domain.Store;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//불변 DTO용, 모든 필드를 private final로 만들고, getter + all-args 생성자 + equals/hashcode + toString 생성.
//Setter없음. 생성 시점 이후 값 변경 불가
//응답 DTO, 조회결과 DTO에 아주 좋음.
// 주의 : 요청 DTO(바디 역직렬화)엔 불편할 수 있음.(기본 생성자가 없어 record나 @NoArgsConstructor가 있는 DTO가 편할 수 있음.)

@Value
@Builder
public class StoreResponse {

    Long storeId;
    String name;
    String address;
    String phone;
    String description;
    String openTime;
    String closeTime;
    BigDecimal latitude;
    BigDecimal longitude;
    LocalDateTime createdAt;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .description(store.getDescription())
                .openTime(store.getOpenTime() == null ? null : store.getOpenTime().toString())
                .closeTime(store.getCloseTime()== null ? null : store.getCloseTime().toString())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
    }
}
