package com.greenUs.server.purchase.dto.response;

import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import lombok.Builder;
import lombok.Getter;

import com.greenUs.server.member.domain.Member;


import java.util.List;

@Getter
public class PurchaseResponse {

    private Long id;
    private Member member;
    private Delivery delivery;
    private List<PurchaseProduct> purchaseProducts;
    private int totalPrice;

    @Builder
    public PurchaseResponse(Long id, Member member, Delivery delivery, List<PurchaseProduct> purchaseProducts, int totalPrice) {
        this.id = id;
        this.member = member;
        this.delivery = delivery;
        this.purchaseProducts = purchaseProducts;
        this.totalPrice = totalPrice;
    }
}