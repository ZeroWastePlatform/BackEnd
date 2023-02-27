package com.greenUs.server.product.dto.respond;


import lombok.Builder;

import java.util.List;

@Builder
public class GetProductDetailDto {
    private int price;
    private String title;
    private List<String> thumbnail;
    private String summary;
    private int liked;
    private String category;
    private String badges;
}
