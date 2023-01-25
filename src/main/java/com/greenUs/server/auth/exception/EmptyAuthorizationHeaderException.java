package com.greenUs.server.auth.exception;

public class EmptyAuthorizationHeaderException extends RuntimeException{

    public EmptyAuthorizationHeaderException(String message) {
        super(message);
    }

    public EmptyAuthorizationHeaderException() {
        this("AuthorizationHeader 가 존재하지 않습니다.");
    }
}