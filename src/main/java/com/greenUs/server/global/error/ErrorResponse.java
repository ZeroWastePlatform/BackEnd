package com.greenUs.server.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(ErrorCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }
}
