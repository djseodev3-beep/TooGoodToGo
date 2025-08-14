package com.spring.toogoodtogo.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder // 상속 받은 필드를 빌더에서 사용하지 못하는 등의 제한이 있었지만 생성자에서 상속받은 필드도 빌더에서 사용할 수 있다.
//@Entity는 이클래스는 테이블과 직접 1:1 매핑된다는 의미,
//@MappedSuperclass는 이 클래스의 필드만 자식 엔티티에 상속해라, 테이블로 직접 생성하지 않고 자식 @Entity에만 반영
//@Entity은 MappedSuperclass랑 같이 쓰면 안된다는데?
@MappedSuperclass // Entity 마다 공통된 필드를 사용하기 위해 사용하는 어노테이션
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

