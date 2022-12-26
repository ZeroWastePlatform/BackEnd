package com.greenUs.server.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ProductForm {
    private Long id;
    private String title;
    private String brand;
    private String image;
    private int price;
}
