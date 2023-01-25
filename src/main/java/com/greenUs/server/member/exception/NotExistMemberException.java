package com.greenUs.server.member.exception;

public class NotExistMemberException extends RuntimeException{

    public NotExistMemberException(String message) {
        super(message);
    }

    public NotExistMemberException() {
        this("존재하지 않는 회원입니다.");
    }
}
