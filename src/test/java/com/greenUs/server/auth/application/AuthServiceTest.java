// package com.greenUs.server.auth.application;
//
// import com.greenUs.server.auth.dto.OAuthMember;
// import com.greenUs.server.auth.dto.request.TokenRenewalRequest;
// import com.greenUs.server.auth.dto.response.AccessRefreshTokenResponse;
// import com.greenUs.server.auth.dto.response.AccessTokenResponse;
// import com.greenUs.server.auth.exception.InvalidTokenException;
// import com.greenUs.server.member.domain.Member;
// import com.greenUs.server.member.domain.SocialType;
// import com.greenUs.server.member.repository.MemberRepository;
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.transaction.annotation.Transactional;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.junit.jupiter.api.Assertions.*;
//
// @SpringBootTest
// @ActiveProfiles("test")
// class AuthServiceTest {
//
//     @Autowired
//     AuthService authService;
//
//     @Autowired
//     MemberRepository memberRepository;
//
//     @Transactional
//     @DisplayName("회원 AccessToken, RefreshToken 생성 후 반환")
//     @Test
//     void 회원_AccessToken_RefreshToken_생성_후_반환() {
//         // given
//         Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
//         memberRepository.save(member);
//
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//
//         // when
//         AccessRefreshTokenResponse response = authService.generateAccessAndRefreshToken(oAuthMember);
//
//         // then
//         assertAll(() -> {
//             assertThat(response.getAccessToken()).isNotEmpty();
//             assertThat(response.getRefreshToken()).isNotEmpty();
//         });
//     }
//
//     @Transactional
//     @DisplayName("인가 코드 있는 회원 DB 에 자동 저장")
//     @Test
//     void 인가_코드_있는_회원_DB에_자동_저장() {
//         // given
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//
//         // when
//         AccessRefreshTokenResponse response = authService.generateAccessAndRefreshToken(oAuthMember);
//
//         // then
//         assertThat(memberRepository.existsByEmail(oAuthMember.getEmail())).isTrue();
//     }
//
//     @Transactional
//     @DisplayName("이미 가입 되어 있는 회원이면 인가 코드 있는 회원 DB 자동 저장 X")
//     @Test
//     void 이미_가입_되어_있는_회원이면_인가코드_있는_회원_DB_자동_저장X() {
//         // given
//         Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
//         memberRepository.save(member);
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//
//         // when
//         AccessRefreshTokenResponse response = authService.generateAccessAndRefreshToken(oAuthMember);
//
//         // then
//         assertThat(memberRepository.findAll()).hasSize(1);
//     }
//
//     @Transactional
//     @DisplayName("이미 가입 되어 있는 회원이면 기존 refreshToken 반환")
//     @Test
//     void 이미_가입_되어_있는_회원이면_기존_refreshToken_반환() {
//         // given
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//
//         // when
//         AccessRefreshTokenResponse response1 = authService.generateAccessAndRefreshToken(oAuthMember);
//         AccessRefreshTokenResponse response2 = authService.generateAccessAndRefreshToken(oAuthMember);
//
//         // then
//         assertThat(response1.getRefreshToken()).isEqualTo(response2.getRefreshToken());
//     }
//
//     @Transactional
//     @DisplayName("refreshToken 으로 새로운 accessToken 발급")
//     @Test
//     void refreshToken_으로_새로운_accessToken_발급() {
//         // given
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//         AccessRefreshTokenResponse accessRefreshTokenResponse = authService.generateAccessAndRefreshToken(oAuthMember);
//         TokenRenewalRequest request = new TokenRenewalRequest(accessRefreshTokenResponse.getRefreshToken());
//         // when
//         AccessTokenResponse accessTokenResponse = authService.generateAccessToken(request);
//
//         // then
//         assertThat(accessTokenResponse.getAccessToken()).isNotEmpty();
//     }
//
//     @Transactional
//     @DisplayName("잘못된 refreshToken 으로 accessToken 발급할 때 예외")
//     @Test
//     void 잘못된_refreshToken_으로_accessToken_발급할_때_예외() {
//         // given
//         OAuthMember oAuthMember = new OAuthMember("greenus@naver.com", "name", "img_url", "token");
//         authService.generateAccessAndRefreshToken(oAuthMember);
//         TokenRenewalRequest request = new TokenRenewalRequest("wrongToken");
//
//         // when & then
//         assertThatThrownBy(() -> authService.generateAccessToken(request))
//                 .isInstanceOf(InvalidTokenException.class);
//
//     }
// }