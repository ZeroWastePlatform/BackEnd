package com.greenUs.server.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class GetReviewDto {
    private float avgRate;
    private String rate;
    private int total;
}
