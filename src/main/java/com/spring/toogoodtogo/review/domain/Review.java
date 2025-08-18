package com.spring.toogoodtogo.review.domain;

import com.spring.toogoodtogo.global.entity.BaseTimeEntity;
import com.spring.toogoodtogo.reservation.domain.Reservation;
import com.spring.toogoodtogo.store.domain.Store;
import com.spring.toogoodtogo.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Review extends BaseTimeEntity {

    // 예약 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 리뷰 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 매장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private int rating; // 1 ~ 5

    @Column(nullable = false, length = 500)
    private String content;

}