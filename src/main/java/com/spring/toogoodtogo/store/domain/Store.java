package com.spring.toogoodtogo.store.domain;

import com.spring.toogoodtogo.global.BaseAuditableEntity;
import com.spring.toogoodtogo.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "stores")
@AttributeOverride(name = "id", column = @Column(name = "store_id")) // PK 컬럼명을 Store_id로
public class Store extends BaseAuditableEntity {

    // N:1 경우 N인경우에 무조건 외래키를 가지며, @MayToOne 추가
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "owner_id",nullable = false) // FK 컬럼명 : owner_id, 참조 :users.id
    private User ownerId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date openTime;
    @Column(nullable = false)
    private Date closeTime;

    /*@Column(nullable = false)
    private boolean isApproved;*/

    //경도(X, 126.XXXXXX)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    //위도(Y, 36.XXXXXX)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;



}
