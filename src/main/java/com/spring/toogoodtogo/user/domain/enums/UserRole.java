package com.spring.toogoodtogo.user.domain.enums;

public enum UserRole {
    CUSTOMER("고객"),
    STORE_OWNER("사장"),
    ADMIN("관리자");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
