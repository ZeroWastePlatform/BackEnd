package com.greenUs.server.product.dto.request;

import lombok.Getter;

@Getter
public class LikeDto {
    private boolean like;
    private Long productId;
}
