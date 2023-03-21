package com.greenUs.server.product.exception;

public class NotFoundProductException extends RuntimeException{

    public NotFoundProductException(String message) {
        super(message);
    }

    public NotFoundProductException() {
        this("상품이 존재하지 않습니다.");
    }
}
