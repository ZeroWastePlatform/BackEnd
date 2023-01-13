package com.greenUs.server.product.dto.respond;


import lombok.Builder;

@Builder
public class GetProductDetailDto {
    private int price;
    private String name;
    private String thumbnail;
    private String description;
}
