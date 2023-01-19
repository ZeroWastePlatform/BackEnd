package com.greenUs.server.member.exception;

public class InvalidMemberException extends RuntimeException{

    public InvalidMemberException(String message) {
        super(message);
    }

    public InvalidMemberException() {
        this("유저가 존재하지 않습니다");
    }
}
