package com.greenUs.server.reviewAndAsk.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewResponse {
    private int rate;
    private String content;
    private String photo;

}
