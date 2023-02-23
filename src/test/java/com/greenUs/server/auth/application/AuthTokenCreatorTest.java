package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import com.greenUs.server.auth.exception.InvalidTokenException;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import com.greenUs.server.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthTokenCreatorTest {

    @Autowired
    TokenCreator tokenCreator;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("accessToken, refreshToken 을 생성한다")
    @Test
    void accessToken_refreshToken_생성() {
        // given
        Long memberId = 1L;

        // when
        AuthToken authToken = tokenCreator.createAuthToken(memberId);

        // then
        assertThat(authToken.getAccessToken()).isNotEmpty();
        assertThat(authToken.getRefreshToken()).isNotEmpty();
    }

    @DisplayName("refreshToken 으로 accessToken 생성")
    @Test
    void refreshToken_으로_accessToken_생성() {
        // given
        Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
        memberRepository.save(member);
        AuthToken authToken = tokenCreator.createAuthToken(member.getId());

        // when
        AuthToken renewAuthToken = tokenCreator.renewAuthToken(authToken.getRefreshToken());

        // then
        assertThat(renewAuthToken.getAccessToken()).isNotEmpty();
        assertThat(renewAuthToken.getRefreshToken()).isNotEmpty();
    }

    @DisplayName("존재하지 않는 유저의 refreshToken 으로 accessToken 생성시 유효성 검사")
    @Test
    void 존재하지_않는_유저의_refreshToken_으로_accessToken_생성시_유효성_검사() {
        // given
        Long memberId = 0L;
        AuthToken authToken = tokenCreator.createAuthToken(memberId);

        // when & then
        assertThatThrownBy(() -> tokenCreator.renewAuthToken(authToken.getRefreshToken()))
                .isInstanceOf(InvalidTokenException.class);
    }

    @DisplayName("accessToken 에서 payload 추출")
    @Test
    void accessToken_에서_payload_추출() {
        // given
        Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
        memberRepository.save(member);
        AuthToken authToken = tokenCreator.createAuthToken(member.getId());

        // when
        Long extractId = tokenCreator.extractPayload(authToken.getAccessToken());

        // then
        assertThat(extractId).isEqualTo(member.getId());
    }

}