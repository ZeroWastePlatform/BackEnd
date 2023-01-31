package com.greenUs.server.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMPTY_TOKEN_HEADER(400, "토큰 헤더가 존재하지 않습니다."),
    ENTITY_NOT_FOUND(400, "엔티티를 찾을 수 없습니다."),
    INVALID_TOKEN(401, "잘못된 토큰입니다."),
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    OAUTH_SERVER_ERROR(500, "OAuth 서버와 통신이 원활하지 않습니다.");


    private int status;
    private String message;
}
