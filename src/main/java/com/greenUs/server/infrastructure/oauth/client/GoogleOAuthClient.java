package com.greenUs.server.infrastructure.oauth.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenUs.server.auth.application.OAuthClient;
import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.response.OAuthAccessTokenResponse;
import com.greenUs.server.global.config.properties.GoogleProperties;
import com.greenUs.server.infrastructure.oauth.dto.GoogleTokenResponse;
import com.greenUs.server.infrastructure.oauth.dto.UserInfo;
import com.greenUs.server.infrastructure.oauth.exception.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class GoogleOAuthClient implements OAuthClient {

    private static final String JWT_DELIMITER = "\\.";

    private final GoogleProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GoogleOAuthClient(final GoogleProperties properties, final RestTemplateBuilder restTemplateBuilder, final ObjectMapper objectMapper) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuthMember getOAuthMember(String code, String redirectUri) {
        GoogleTokenResponse googleTokenResponse = requestGoogleToken(code, redirectUri);

        String idToken = googleTokenResponse.getIdToken();
        String payload = idToken.split(JWT_DELIMITER)[1];
        String decodePayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);

        try {
            UserInfo userInfo = objectMapper.readValue(decodePayload, UserInfo.class);
            return new OAuthMember(userInfo.getEmail(), userInfo.getName(), userInfo.getPicture(), googleTokenResponse.getRefreshToken());
        } catch (JsonProcessingException e) {
            log.error("cannot read id token");
        }
        return null;
    }

    private GoogleTokenResponse requestGoogleToken(final String code, final String redirectUri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = generateTokenParams(code, redirectUri);

        HttpEntity<MultiValueMap<String , String>> request = new HttpEntity<>(params, httpHeaders);
        return fetchGoogleToken(request).getBody();
    }

    private MultiValueMap<String, String> generateTokenParams(final String code, final String redirectUri) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", redirectUri);
        return params;
    }

    private ResponseEntity<GoogleTokenResponse> fetchGoogleToken(
            final HttpEntity<MultiValueMap<String, String>> request
    ) {
        try {
            return restTemplate.postForEntity(properties.getTokenUri(), request, GoogleTokenResponse.class);
        } catch (RestClientException e) {
            throw new OAuthException(e);
        }
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(String refreshToken) {
        return null;
    }


}
