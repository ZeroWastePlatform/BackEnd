package com.greenUs.server.infrastructure.oauth.exception;

public class OAuthException extends RuntimeException {

    public OAuthException() {
        super("OAuth 서버와 통신 과정에서 문제 발생");
    }

    public OAuthException(final Exception e) {
        super("OAuth 서버와 통신 과정에서 문제 발생", e);
    }

    public OAuthException(final String message, final Exception e) {
        super(message, e);
    }
}
