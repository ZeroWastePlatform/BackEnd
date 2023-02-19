package com.greenUs.server.post.exception;

public class NotFoundPostException extends RuntimeException {

    public NotFoundPostException(String message) {
        super(message);
    }

    public NotFoundPostException() {
        this("게시글이 존재하지 않습니다");
    }
}
