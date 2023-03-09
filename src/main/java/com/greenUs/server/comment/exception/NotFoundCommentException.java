package com.greenUs.server.comment.exception;

public class NotFoundCommentException extends RuntimeException {

    public NotFoundCommentException(String message) {
        super(message);
    }

    public NotFoundCommentException() {
        this("댓글이 존재하지 않습니다");
    }
}
