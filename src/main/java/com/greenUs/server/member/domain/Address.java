package com.greenUs.server.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "zipcode")
    private String zipCode; // 우편번호

    @Column(name = "address")
    private String address; // 배송지 (ex. 서울시 서초구 서초동)

    @Column(name = "addressDetail")
    private String addressDetail; // 상세 정보 (ex. 501호)

    @Builder
    protected Address(String zipCode, String address, String addressDetail) {
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
