package com.greenUs.server.product.dto.respond;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRecommendDto {
    private long id;
    private String category;
    private String brand;
    private String title;
    private String discountRate;
    private String price;
    private String badges;
    private boolean liked;
}
