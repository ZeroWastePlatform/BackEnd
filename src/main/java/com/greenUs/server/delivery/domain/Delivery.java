package com.greenUs.server.delivery.domain;

import com.greenUs.server.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    private String addressName; // 배송지명 (ex. 우리집)
    private String recipient; // 받는 사람
    private String recipientPhone; // 받는 사람 번호
    private int zipCode; // 우편번호
    private String address; // 배송지 (ex. 서울시 서초구 서초동)
    private String addressDetail; // 상세 정보 (ex. 501호)
    private String deliverDate;

    public Delivery (String addressName, String recipient, String recipientPhone, int zipCode, String address, String addressDetail, String deliverDate) {
        this.addressName = addressName;
        this.recipient = recipient;
        this.recipientPhone = recipientPhone;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.deliverDate = deliverDate;
    }
}
