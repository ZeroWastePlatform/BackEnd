package com.greenUs.server.product.exception;

public class LikeDuplicateException extends RuntimeException{

    public LikeDuplicateException(String message) {
        super(message);
    }

    public LikeDuplicateException() {
        this("좋아요가 이미 눌렸습니다.");
    }
}
