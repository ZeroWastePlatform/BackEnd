package com.greenUs.server.product.dto;

import lombok.Getter;

@Getter
public enum Price {

    LT_10("10000원 미만"),
    BT_10_30("10000원 이상 30000원 이하"),
    BT_30_50("30000원 이상 50000원 이하"),
    GT_50("50000원 초과");


    private String name;

    Price(String name) {
        this.name = name;
    }
}
