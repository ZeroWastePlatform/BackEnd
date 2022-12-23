package com.greenUs.server.product.domain;

public enum ProductStatus {

    SOLD_OUT("품절"),IN_STOCK("재고 남음");

    private String name;

    ProductStatus(String name) {
        this.name = name;
    }
}
