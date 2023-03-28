package com.greenUs.server.purchase.dto.response;

import com.greenUs.server.product.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PurchaseProductResponse {

    private Long productId;

    private String thumbnail;

    private Brand brand;

    private String title;

    private Integer price;
}
