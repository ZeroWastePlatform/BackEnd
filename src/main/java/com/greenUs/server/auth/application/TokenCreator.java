package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;

public interface TokenCreator {

    AuthToken createAuthToken(Long memberId);
    String renewAccessToken(String refreshToken);
    Long extractPayload(String accessToken);
    void deleteRefreshToken(String refreshToken);
}
