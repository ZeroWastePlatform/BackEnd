package com.greenUs.server.review.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetReviewDetailDto {
    private String avatar;
    private String nickname;
    private int rate;
    private LocalDateTime date;
    private boolean liked;
    private int likedCount;
    private String content;
    private String photo;
}
