package com.greenUs.server.product.domain;

import com.greenUs.server.product.exception.ConvertFailException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Badge {
    BEST("best", "1"),
    NEW("new", "2"),
    SALE("sale", "3"),
    BEST_SALE("best-sale", "4"),
    BEST_NEW("best-new", "5"),
    NEW_SALE("new-sale", "6"),
    BEST_NEW_SALE("best-new-sale", "7");

    private String name;
    private String code;

    Badge(String name, String code) {
        this.name=name;
        this.code=code;
    }

    public static Badge ofCode(String code) {
        return Arrays.stream(Badge.values())
                .filter(v->v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertFailException(String.format("DB 상에 %s 가 없습니다.", code)));
    }
}
