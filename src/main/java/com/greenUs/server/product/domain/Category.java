package com.greenUs.server.product.domain;

import com.greenUs.server.product.exception.ConvertFailException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    FOOD("식품","1"),
    KITCHEN("주방","2"),
    BATH("욕실", "3"),
    LIFE("생활", "4"),
    HOBBY("취미","5"),
    GIFT("선물","6"),
    WOMAN("여성용품", "7"),
    PET("반려동물","8"),
    STATIONERY("문구","9");
    private String name;
    private String code;

    Category(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static Category ofCode(String code) {
        return Arrays.stream(Category.values())
                .filter(v->v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertFailException(String.format("DB 상에 %s 가 없습니다.", code)));
    }
}
