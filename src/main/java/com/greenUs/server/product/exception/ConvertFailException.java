package com.greenUs.server.product.exception;

public class ConvertFailException extends RuntimeException {

    public ConvertFailException(String message) {
        super(message);
    }

    public ConvertFailException() {
        this("코드가 잘못됐습니다.");
    }
}
