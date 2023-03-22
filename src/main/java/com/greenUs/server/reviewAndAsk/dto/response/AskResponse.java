package com.greenUs.server.reviewAndAsk.dto.response;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AskResponse {
    private Long id;
    private String title;
    private String content;
    private boolean secret;
    private String answer;
}
