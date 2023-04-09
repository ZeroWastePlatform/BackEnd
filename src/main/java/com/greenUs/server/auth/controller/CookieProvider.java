package com.greenUs.server.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieProvider {

    protected static final String REFRESH_TOKEN = "refreshToken";
    private static final int REMOVE_AGE = 0;
    private final long refreshTokenValidity;

    public CookieProvider(@Value("${security.jwt.token.refresh.expire-length}") long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .maxAge(Duration.ofMillis(refreshTokenValidity))
                .build();
    }

    public ResponseCookie deleteCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .maxAge(Duration.ofMillis(REMOVE_AGE))
                .build();
    }
}
