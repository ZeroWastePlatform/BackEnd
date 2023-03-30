package com.greenUs.server.member.dto.response;

import com.greenUs.server.product.domain.Brand;
import lombok.Builder;
import lombok.Getter;

// TODO: 아직 DTO 완성단계 아님
@Getter
@Builder
public class MyPagePurchaseResponse {
    private Long productId;
    private Brand brand;
    private String title;
    private String thumbnail;
    private int price;
    private int count;
}
