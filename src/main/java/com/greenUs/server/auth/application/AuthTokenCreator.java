package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import com.greenUs.server.auth.domain.RefreshToken;
import com.greenUs.server.auth.exception.InvalidTokenException;
import com.greenUs.server.auth.exception.RefreshTokenNotExistException;
import com.greenUs.server.auth.repository.RefreshTokenRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class AuthTokenCreator implements TokenCreator{

    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthTokenCreator(
            @Value("${security.jwt.token.secret-key}") String secretKey,
            @Value("${security.jwt.token.access.expire-length}") long accessTokenValidity,
            @Value("${security.jwt.token.refresh.expire-length}") long refreshTokenValidity,
            MemberRepository memberRepository,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public AuthToken createAuthToken(Long memberId) {
        String accessToken = createAccessToken(String.valueOf(memberId), accessTokenValidity);
        String refreshToken = createRefreshToken(memberId);
        return new AuthToken(accessToken, refreshToken);
    }

    private String createAccessToken(String payload, Long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String renewAccessToken(String refreshToken) {
        checkRefreshToken(refreshToken);

        RefreshToken token = refreshTokenRepository.findById(refreshToken).orElseThrow(RefreshTokenNotExistException::new);
        return createAccessToken(String.valueOf(token.getMemberId()), accessTokenValidity);
    }

    @Override
    public Long extractPayload(String accessToken) {
        validateToken(accessToken);
        return Long.valueOf(getPayload(accessToken));
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {

        checkRefreshToken(refreshToken);
        refreshTokenRepository.findById(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

    private void validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            claimsJws.getBody()
                    .getExpiration()
                    .before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("권한이 없습니다");
        }
    }

    private String getPayload(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Transactional
    public String createRefreshToken(Long memberId) {
        RefreshToken refreshToken
                = new RefreshToken(UUID.randomUUID().toString(), memberId, refreshTokenValidity);
        return refreshTokenRepository.save(refreshToken).getRefreshToken();
    }

    private void checkRefreshToken(String refreshToken) {
        if (refreshToken.isBlank())
            throw new IllegalArgumentException("refresh 토큰 비어있음");
    }
}
