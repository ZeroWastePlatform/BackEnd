package com.greenUs.server.purchase.dto.response;

import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import lombok.Getter;

import java.util.List;

@Getter
public class PurchaseResponse {

    private Long id;
    private Member member;
    private Delivery delivery;
    private List<PurchaseProduct> purchaseProducts;
    private int totalPrice;

    public PurchaseResponse(Purchase purchase) {
        this.id = purchase.getId();
        this.member = purchase.getMember();
        this.delivery = purchase.getDelivery();
        this.purchaseProducts = purchase.getPurchaseProducts();
        this.totalPrice = purchase.getTotalPrice();
    }
}
