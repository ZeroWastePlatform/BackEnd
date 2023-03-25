package com.greenUs.server.member.dto.response;

import com.greenUs.server.product.domain.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MyPageLikeProductResponse {

    private Long id;
    private Brand brand;
    private String title;
    private Integer price;
    private Integer likeCount;
    private Integer discountRate;
}
