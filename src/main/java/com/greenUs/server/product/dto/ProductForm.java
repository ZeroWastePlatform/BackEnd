package com.greenUs.server.product.dto;

import lombok.Builder;

@Builder
public class ProductForm {
    private Long id;
    private String title;
    private String Brand;
    private String image;
    private int price;
}
