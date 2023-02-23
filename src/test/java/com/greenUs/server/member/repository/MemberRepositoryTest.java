// package com.greenUs.server.member.repository;
//
// import com.greenUs.server.member.domain.Member;
// import com.greenUs.server.member.domain.SocialType;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.ActiveProfiles;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.*;
//
// @DataJpaTest
// @ActiveProfiles("test")
// class MemberRepositoryTest {
//
//     @Autowired
//     MemberRepository memberRepository;
//
//     @DisplayName("중복된 이메일 체크")
//     @Test
//     void existsByEmail() {
//         // given
//         Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
//         Member saveMember = memberRepository.save(member);
//
//         // when & then
//         assertTrue(memberRepository.existsByEmail(saveMember.getEmail()));
//     }
//
//     @DisplayName("이메일로 회원 찾기")
//     @Test
//     void findByEmail() {
//         // given
//         Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
//         memberRepository.save(member);
//
//         // when
//         Member findMember = memberRepository.findByEmail("greenus@naver.com");
//
//         // then
//         assertThat(member.getEmail()).isEqualTo(findMember.getEmail());
//     }
// }