package com.greenUs.server.product.dto;

import lombok.Getter;


@Getter
public enum ProductStatus {

    FREE_SHIPPING("무료배송"),
    DISCOUNT_STOCK("할인상품"),
    REMOVE_OUT_OF_STOCK("품절상품 제외");


    private String name;

    ProductStatus(String name) {
        this.name = name;
    }
}
