package com.greenUs.server.member.exception;

public class NotFoundMemberException extends RuntimeException{

    public NotFoundMemberException(String message) {
        super(message);
    }

    public NotFoundMemberException() {
        this("유저가 존재하지 않습니다");
    }
}
