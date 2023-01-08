package com.greenUs.server.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// OAuth 인증 URI 전달하는 DTO
// 소셜 로그인 링크
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUriResponse {

    private String oAuthUri;
}
