package com.greenUs.server.purchase.dto.request;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.dto.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseRequest {

    private String name;
    private String phoneNum;
    private String email;
    private ShippingAddress address;
    private int totalPrice;
}
