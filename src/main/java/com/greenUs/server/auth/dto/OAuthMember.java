package com.greenUs.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthMember {

    private final String email;
    private final String displayName;
    private final String profileImageUrl;
    private final String refreshToken;
}
