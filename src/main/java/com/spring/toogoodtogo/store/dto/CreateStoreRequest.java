package com.spring.toogoodtogo.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

// getter + setter + equals/hashCode + toString + required-args ctor
//엔티티에는 지양 하는 이유는 Setter 남발, equals/hashcode가 연관관계/지연로딩과 충돌하기 쉬움.
//필요하면 @EqualsAndHashCode(onlyExplicitlyIncluded = true)로 제어
@Data
public class CreateStoreRequest {

    @NotBlank @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    private String address;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String description;

    // "09:00" 형식이면 컨버터를두거나 String으로 받아 서비스에서 LocalTime 파싱
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private BigDecimal latitude;
    private BigDecimal longitude;

}
