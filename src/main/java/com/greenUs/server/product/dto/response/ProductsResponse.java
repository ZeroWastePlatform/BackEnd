package com.greenUs.server.product.dto.response;

import com.greenUs.server.product.domain.Badge;
import com.greenUs.server.product.domain.Brand;
import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.domain.ProductStatus;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class ProductsResponse {

    private Long id;

    private Category category;

    private String discountRate;

    private Badge badges;

    private String title;

    private String description;

    private Brand brand;

    private ProductStatus productStatus;

    private Integer viewCount;

    private Integer price;

    private Integer deliveryFee;

    private Integer likeCount;

    private Integer reviewCount;
}
