package com.greenUs.server.purchase.dto.response;

import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.purchase.domain.Purchase;
import lombok.Getter;

@Getter
public class PurchaseResponse {

    private Long id;
    private Delivery delivery;
    private int totalPrice;

    public PurchaseResponse(Purchase purchase) {
        this.id = purchase.getId();
        this.delivery = purchase.getDelivery();
        this.totalPrice = purchase.getTotalPrice();
    }
}
