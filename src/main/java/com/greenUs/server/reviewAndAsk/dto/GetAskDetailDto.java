package com.greenUs.server.reviewAndAsk.dto;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetAskDetailDto {
    private Long id;
    private String title;
    private String category;
    private String nickName;
    private LocalDateTime date;
    private String content;
    private String answer;
    private boolean secret;
}
