package com.greenUs.server.product.domain;

import com.greenUs.server.product.exception.ConvertFailException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    FOOD("food","1"),
    KITCHEN("kitchen","2"),
    BATH("bath", "3"),
    LIFE("life", "4"),
    HOBBY("hobby","5"),
    GIFT("gift","6"),
    WOMAN("woman", "7"),
    PET("pet","8"),
    STATIONERY("stationery","9");
    private String desc;
    private String code;

    Category(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public static Category ofCode(String code) {
        return Arrays.stream(Category.values())
                .filter(v->v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertFailException(String.format("DB 상에 %s 가 없습니다.", code)));
    }
}
