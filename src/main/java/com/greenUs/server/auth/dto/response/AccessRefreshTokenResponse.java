package com.greenUs.server.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessRefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
    private boolean isNewMember;
}
