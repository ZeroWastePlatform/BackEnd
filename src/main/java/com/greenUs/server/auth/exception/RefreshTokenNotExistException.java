package com.greenUs.server.auth.exception;

public class RefreshTokenNotExistException extends RuntimeException{

    public RefreshTokenNotExistException(String message) {
        super(message);
    }
    public RefreshTokenNotExistException() {
        this("refresh token 이 없습니다.");
    }
}
