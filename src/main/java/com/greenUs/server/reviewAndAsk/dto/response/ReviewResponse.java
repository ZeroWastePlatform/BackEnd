package com.greenUs.server.reviewAndAsk.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewResponse {
    private Long id;
    private String photoUrl;
    private String content;
    private Integer rate;
    private Integer likedCount;
}
