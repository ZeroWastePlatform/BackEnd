package com.greenUs.server.product.dto;

import lombok.Getter;

@Getter
public enum Order {
    TOP6("top6"),
    POPULARITY("likeCount"),
    NEW("createdAt"),
    LOW_PRICE("price"),
    HIGH_PRICE("price");

    private final String name;
    Order(String name) {
        this.name=name;
    }
}
