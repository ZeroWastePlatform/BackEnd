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
    REFRESH_TOKEN_NOT_FOUND(401, "리프레시 토큰이 없습니다."),
    POST_MEMBER_NOT_EQUAL(403, "게시글의 작성자가 아닙니다."),
    POST_COMMENT_NOT_EQUAL(403,"해당하는 게시글의 댓글 번호가 아닙니다."),
    POST_ATTACHMENT_NOT_EQUAL(403, "게시글의 첨부파일 이름이 아닙니다."),
    COMMENT_MEMBER_NOT_EQUAL(403, "댓글의 작성자가 아닙니다."),
    BASKET_MEMBER_NOT_EQUAL(403, "장바구니 소유자가 아닙니다."),
    OBJECT_NOT_FOUND(404,"이미 삭제된 파일로 찾을 수 없습니다."),
    POST_NOT_FOUND(404,"게시글을 찾을 수 없습니다."),
    BASKET_NOT_FOUND(404,"장바구니를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(404,"댓글을 찾을 수 없습니다."),
    LIKE_DUPLICATE(409, "좋아요가 중복되었습니다."),
    RESIZE_SERVER_ERROR(500, "파일 리사이즈에 실패했습니다."),
    OAUTH_SERVER_ERROR(500, "OAuth 서버와 통신이 원활하지 않습니다."),
    OUTPUT_STREAM_ERROR(501, "Output Stream 변환에 실패했습니다.");


    private int status;
    private String message;
}
