package com.greenUs.server.product.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class GetInfoDto {
    private int review;
    private int ask;
}
