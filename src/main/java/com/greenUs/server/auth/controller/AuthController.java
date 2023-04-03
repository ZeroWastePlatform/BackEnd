package com.greenUs.server.auth.controller;

import com.greenUs.server.auth.application.AuthService;
import com.greenUs.server.auth.application.OAuthClient;
import com.greenUs.server.auth.application.OAuthUri;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.request.TokenRenewalRequest;
import com.greenUs.server.auth.dto.request.TokenRequest;
import com.greenUs.server.auth.dto.response.AccessRefreshTokenResponse;
import com.greenUs.server.auth.dto.response.AccessTokenResponse;
import com.greenUs.server.auth.dto.response.OAuthUriResponse;
import com.greenUs.server.auth.exception.RefreshTokenNotExistException;
import com.greenUs.server.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.greenUs.server.auth.controller.CookieProvider.REFRESH_TOKEN;

@Tag(name = "인증", description = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthUri oAuthUri;
    private final OAuthClient oAuthClient;
    private final AuthService authService;
    private final CookieProvider cookieProvider;

    @Operation(summary = "로그인 화면으로 가기 위한 url 반환", description = "로그인 화면으로 가기 위한 url 반환 로그인에 성공하면 지정된 redirect uri 로 인가 코드 발급")
    @ApiResponse(responseCode = "200", description = "로그인 화면으로 가능 url 반환", content = @Content(schema = @Schema(implementation = OAuthUriResponse.class)))
    @GetMapping("/{oauthProvider}/oauth-uri")
    public ResponseEntity<OAuthUriResponse> generateLink(
            @Parameter(description = "oauthProvider(GOOGLE, KAKAO, NAVER)", in = ParameterIn.PATH) @PathVariable final String oauthProvider,
            @Parameter(description = "redirectUri", in = ParameterIn.QUERY) @RequestParam final String redirectUri) {

        OAuthUriResponse oAuthUriResponse = new OAuthUriResponse(oAuthUri.generate(redirectUri));
        return ResponseEntity.ok(oAuthUriResponse);
    }

    @Operation(summary = "토큰 발급", description = "인가 코드를 바탕으로 accessToken 과 refreshToken 을 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공 | 최초 가입일 경우 null 반환", content = @Content(schema = @Schema(implementation = AccessRefreshTokenResponse.class))),
            @ApiResponse(responseCode = "500", description = "OAuth 서버와의 연동 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{oauthProvider}/token")
    public ResponseEntity<AccessRefreshTokenResponse> generateAccessRefreshToken(
            @Parameter(description = "oauthProvider(GOOGLE, KAKAO, NAVER)", in = ParameterIn.PATH) @PathVariable final String oauthProvider,
            @Valid @RequestBody final TokenRequest tokenRequest) {

        OAuthMember oAuthMember = oAuthClient.getOAuthMember(tokenRequest.getCode(), tokenRequest.getRedirectUri());
        AccessRefreshTokenResponse accessRefreshTokenResponse = authService.generateAccessAndRefreshToken(oAuthMember);
        ResponseCookie cookie = cookieProvider.createCookie(accessRefreshTokenResponse.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessRefreshTokenResponse);
    }

    @Operation(summary = "accessToken 발급", description = "refreshToken 으로 accessToken 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공", content = @Content(schema = @Schema(implementation = AccessTokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "refreshToken 값 잘못됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/token/access")
    public ResponseEntity<AccessTokenResponse> generateAccessToken(
            @Valid @RequestBody TokenRenewalRequest tokenRenewalRequest
    ) {
        AccessTokenResponse accessTokenResponse = authService.generateAccessToken(tokenRenewalRequest);
        return ResponseEntity.ok(accessTokenResponse);
    }

    @Operation(summary = "token 유효한지 확인", description = "token 유효한지 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 유효하면 null 반환"),
            @ApiResponse(responseCode = "401", description = "Token 값 잘못됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/validate/token")
    public ResponseEntity<Void> validateToken(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = REFRESH_TOKEN, required = false) final String refreshToken
    ) {
        if (refreshToken == null)
            throw new RefreshTokenNotExistException();
        ResponseCookie cookie = cookieProvider.deleteCookie(refreshToken);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
