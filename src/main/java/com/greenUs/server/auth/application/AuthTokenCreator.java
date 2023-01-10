package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthTokenCreator implements TokenCreator{

    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public AuthTokenCreator(
            @Value("${security.jwt.token.secret-key}") String secretKey,
            @Value("${security.jwt.token.access.expire-length}") long accessTokenValidity,
            @Value("${security.jwt.token.refresh.expire-length}") long refreshTokenValidity
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Override
    public AuthToken createAuthToken(Long memberId) {
        String accessToken = createToken(String.valueOf(memberId), accessTokenValidity);
        String refreshToken = createToken(String.valueOf(memberId), refreshTokenValidity);
        return new AuthToken(accessToken, refreshToken);
    }

    private String createToken(String payload, Long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
