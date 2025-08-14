package com.spring.toogoodtogo.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Spring Data JPA에서 Entity의 상태를 감시(Audit)하기위해 Auditing 기능 포함.
public abstract class BaseTimeEntity extends BaseIdEntity {

    // Hibernate에서는 엔티티 객체에 대해 Insert,Update 등의 쿼리가 발생할 때 현재 시간을 자동으로 저장 해주는 어노테이션
    // CreationTimeStamp는 Insert 쿼리가 발생할 때, 현재 시간을 값으로 채워서 쿼리를 생성. @InsertTimeStamp 어노테이션을 사용하면 데이터가 생성된 시점에 대한 관리하는 수고를 덜 수 있다.
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

}
