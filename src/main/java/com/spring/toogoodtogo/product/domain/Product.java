package com.spring.toogoodtogo.product.domain;

import com.spring.toogoodtogo.global.entity.BaseTimeEntity;
import com.spring.toogoodtogo.store.domain.Store;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(name = "products")
public class Product extends BaseTimeEntity {

    // 매장 N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id",nullable = false)
    private Store store;
    @Column(nullable = false,length = 100)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Integer originalPrice;
    @Column(nullable = false)
    private Integer discountPrice;
    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime pickupStartDate;
    private LocalDateTime pickupEndDate;
    private LocalDate available_date;

    private String imageUrl;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private ProductStatus status;

}
