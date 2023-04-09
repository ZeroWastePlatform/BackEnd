package com.greenUs.server.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private String refreshToken;

    private Long memberId;

    @TimeToLive
    private long expiration;

    @Builder
    public RefreshToken(String refreshToken, Long memberId, long expiration) {
        this.refreshToken = checkRefreshToken(refreshToken);
        this.memberId = checkUserId(memberId);
        this.expiration = checkExpiration(expiration);
    }

    private String checkRefreshToken(String refreshToken) {
        if (refreshToken.isBlank())
            throw new IllegalArgumentException("refresh 토큰 비어있음");

        return refreshToken;
    }

    private Long checkUserId(Long userId) {
        if (userId < 1)
            throw new IllegalArgumentException("올바르지 않은 유저 아이디");

        return userId;
    }

    private long checkExpiration(long expiration) {
        if (expiration < 1)
            throw new IllegalArgumentException("올바르지 않은 만료 시간");

        return expiration;
    }
}
