package com.greenUs.server.product.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateProductDto {
    private String title;
    private String category;
    private String description;
    private String brand;
    private String discountRate;
    private String thumbnail;
    private String info;
    private String delivery;
    private String refund;
    private int price;
}
