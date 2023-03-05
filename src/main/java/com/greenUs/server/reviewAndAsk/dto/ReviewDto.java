package com.greenUs.server.reviewAndAsk.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDto {
    private int rate;
    private String content;
    private String photo;

}
