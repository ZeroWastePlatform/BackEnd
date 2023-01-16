package com.greenUs.server.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class GetReviewDto {
    private int starCnt;
    private int helpCnt;
    private LocalDateTime createAt;
    private String userName;
    private String description;
    private String image;
}
