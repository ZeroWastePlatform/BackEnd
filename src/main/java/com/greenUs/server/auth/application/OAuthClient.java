package com.greenUs.server.auth.application;

import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.response.OAuthAccessTokenResponse;

public interface OAuthClient {

    OAuthMember getOAuthMember(final String code, final String redirectUri);

    OAuthAccessTokenResponse getAccessToken(final String refreshToken);
}
