package com.greenUs.server.reviewAndAsk.dto;

import lombok.Builder;

@Builder
public class GetReviewDto {
    private float avgRate;
    private String rate;
    private int total;
}
