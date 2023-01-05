package com.greenUs.server.auth.dto.response;

public class OAuthAccessTokenResponse {

    private String accessToken;

    public OAuthAccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
