package com.greenUs.server.reviewAndAsk.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AskDto {
    private String content;
    private boolean secret;
    private String title;
    private String category;
}
