package com.greenUs.server.product.domain;

import com.greenUs.server.product.exception.ConvertFailException;
import lombok.Getter;

import java.util.Arrays;

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

    public static ProductStatus ofCode(String code) {
        return Arrays.stream(ProductStatus.values())
                .filter(v->v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertFailException(String.format("DB 상에 %s 가 없습니다.", code)));

    }
}
