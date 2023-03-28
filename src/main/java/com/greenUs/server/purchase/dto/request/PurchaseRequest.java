package com.greenUs.server.purchase.dto.request;

import com.greenUs.server.purchase.dto.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

    private String name;
    private String phoneNum;
    private String email;
    private ShippingAddress address;
    private List<Long> productsId;
    private int totalPrice;
}
