package com.spring.toogoodtogo.reservation.domain;

import com.spring.toogoodtogo.global.entity.BaseIdEntity;
import com.spring.toogoodtogo.product.domain.Product;
import com.spring.toogoodtogo.store.domain.Store;
import com.spring.toogoodtogo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private ReservationStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime reservedAt;

    private LocalDateTime pickedUpAt;
}
