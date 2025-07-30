package com.spring.toogoodtogo.support;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
//@Entity는 이클래스는 테이블과 직접 1:1 매핑된다는 의미,
//@MappedSuperclass는 이 클래스의 필드만 자식 엔티티에 상속해라, 테이블로 직접 생성하지 않고 자식 @Entity에만 반영
//@Entity은 MappedSuperclass랑 같이 쓰면 안된다는데?
@SuperBuilder // 상속 받은 필드를 빌더에서 사용하지 못하는 등의 제한이 있었지만 생성자에서 상속받은 필드도 빌더에서 사용할 수 있다.
@MappedSuperclass // Entity 마다 공통된 필드를 사용하기 위해 사용하는 어노테이션
@EntityListeners(AuditingEntityListener.class) // Spring Data JPA에서 Entity의 상태를 감시(Audit)하기위해 Auditing 기능 포함.
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hibernate에서는 엔티티 객체에 대해 Insert,Update 등의 쿼리가 발생할 때 현재 시간을 자동으로 저장 해주는 어노테이션
    // CreationTimeStamp는 Insert 쿼리가 발생할 때, 현재 시간을 값으로 채워서 쿼리를 생성. @InsertTimeStamp 어노테이션을 사용하면 데이터가 생성된 시점에 대한 관리하는 수고를 덜 수 있다.
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
