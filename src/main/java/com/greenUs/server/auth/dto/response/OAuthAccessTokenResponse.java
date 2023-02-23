package com.greenUs.server.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthAccessTokenResponse {

    private String accessToken;
}
