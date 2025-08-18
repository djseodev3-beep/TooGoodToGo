package com.spring.toogoodtogo.product.domain;

public enum ProductStatus {

    AVAILABLE("구매 가능"),
    SOLD_OUT("구매 불가능"),
    EXPIRED("만료");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

}
