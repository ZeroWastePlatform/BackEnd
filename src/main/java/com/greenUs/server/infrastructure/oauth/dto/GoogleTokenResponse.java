package com.greenUs.server.infrastructure.oauth.dto;

public class GoogleTokenResponse {

    private String refresh_token;
    private String id_token;

    public GoogleTokenResponse(final String refresh_token, final String id_token) {
        this.refresh_token = refresh_token;
        this.id_token = id_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getIdToken() {
        return id_token;
    }
}
