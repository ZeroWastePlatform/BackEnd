package com.greenUs.server.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateProductDto {
    private String title;
    private String description;
    private String brand;
    private String image;
    private int price;
}
