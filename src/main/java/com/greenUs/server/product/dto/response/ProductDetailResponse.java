package com.greenUs.server.product.dto.response;

import com.greenUs.server.product.domain.Badge;
import com.greenUs.server.product.domain.Brand;
import com.greenUs.server.product.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductDetailResponse {

    private Long id;

    private Category category;

    private Integer discountRate;

    private Badge badges;

    private String title;

    private String description;

    private Brand brand;

    private Integer viewCount;

    private Integer stock;

    private Integer price;

    private Integer deliveryFee;

    private Integer likeCount;

    private Integer reviewCount;

    private Integer askCount;

    private String thumbnail;
}
