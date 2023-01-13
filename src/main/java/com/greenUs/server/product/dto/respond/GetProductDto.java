package com.greenUs.server.product.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class GetProductDto {
    private Long id;
    private String title;
    private String brand;
    private String image;
    private int price;
}
