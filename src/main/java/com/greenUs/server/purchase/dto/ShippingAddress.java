package com.greenUs.server.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {

    private String addressName; // 배송지명 (ex. 우리집)
    private String recipient; // 받는 사람
    private String recipientPhone; // 받는 사람 번호
    private String zipCode; // 우편번호
    private String address; // 배송지 (ex. 서울시 서초구 서초동)
    private String addressDetail; // 상세 정보 (ex. 501호)
}
