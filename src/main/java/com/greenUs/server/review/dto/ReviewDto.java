package com.greenUs.server.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDto {
    private int rate;
    private String content;
    private String photo;

}
