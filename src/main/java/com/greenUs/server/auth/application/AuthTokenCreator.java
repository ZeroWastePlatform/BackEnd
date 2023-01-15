package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import com.greenUs.server.auth.exception.InvalidTokenException;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthTokenCreator implements TokenCreator{

    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
    private final MemberRepository memberRepository;

    public AuthTokenCreator(
            @Value("${security.jwt.token.secret-key}") String secretKey,
            @Value("${security.jwt.token.access.expire-length}") long accessTokenValidity,
            @Value("${security.jwt.token.refresh.expire-length}") long refreshTokenValidity,
            MemberRepository memberRepository
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.memberRepository = memberRepository;
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

    @Override
    public AuthToken renewAuthToken(String refreshToken) {
        validateToken(refreshToken);
        Long memberId = Long.valueOf(getPayload(refreshToken));

        String accessTokenForRenew = createToken(String.valueOf(memberId), accessTokenValidity);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(InvalidTokenException::new);
        String refreshTokenForRenew = member.getToken();

        return new AuthToken(accessTokenForRenew, refreshTokenForRenew);
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
}
