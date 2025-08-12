package com.spring.toogoodtogo.global;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseAuditableEntity extends BaseTimeEntity{


    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected Long updatedBy;
}
