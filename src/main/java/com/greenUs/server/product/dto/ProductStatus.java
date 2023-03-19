package com.greenUs.server.product.domain;

import lombok.Getter;


@Getter
public enum ProductStatus {

    SOLD_OUT("품절", "1"),
    IN_STOCK("재고 있음", "2");


    private String name;
    private String code;

    ProductStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
