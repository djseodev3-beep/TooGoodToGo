package com.spring.toogoodtogo.user.domain;


import com.spring.toogoodtogo.global.BaseIdEntity;
import com.spring.toogoodtogo.user.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING) //숫자 대신 문자열로 저장.
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean enabled;

    public boolean isStoreOwner(){
        return role == UserRole.STORE_OWNER;
    }

}
