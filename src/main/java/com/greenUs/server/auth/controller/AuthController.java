package com.greenUs.server.auth.controller;

import com.greenUs.server.auth.application.OAuthClient;
import com.greenUs.server.auth.application.OAuthUri;
import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.request.TokenRequest;
import com.greenUs.server.auth.dto.response.AccessRefreshTokenResponse;
import com.greenUs.server.auth.dto.response.OAuthUriResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuthUri oAuthUri;
    private final OAuthClient oAuthClient;

    /**
     * 로그인 화면으로 가기 위한 Url 반환
     * 로그인 성공하면 지정된 redirect uri 로 인가 코드 발급
     */
    @GetMapping("/{oauthProvider}/oauth-uri")
    public ResponseEntity<OAuthUriResponse> generateLink(@PathVariable final String oauthProvider,
                                                         @RequestParam final String redirectUri) {

        OAuthUriResponse oAuthUriResponse = new OAuthUriResponse(oAuthUri.generate(redirectUri));
        return ResponseEntity.ok(oAuthUriResponse);
    }


    @PostMapping("/{oauthProvider}/token")
    public ResponseEntity<OAuthMember> getOauthMemberInfo(
            @PathVariable final String oauthProvider,
            @Valid @RequestBody final TokenRequest tokenRequest) {

        OAuthMember oAuthMember = oAuthClient.getOAuthMember(tokenRequest.getCode(), tokenRequest.getRedirectUri());
        return ResponseEntity.ok(oAuthMember);
    }
}
