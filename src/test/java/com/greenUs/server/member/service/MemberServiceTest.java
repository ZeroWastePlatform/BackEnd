package com.greenUs.server.member.service;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    @DisplayName("id 로 회원 조회")
    @Test
    void findById() {
        // given
        Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
        Member saveMember = memberRepository.save(member);

        // when
        MemberResponse memberResponse = memberService.findById(saveMember.getId());

        // then
        assertThat(memberResponse.getId()).isEqualTo(member.getId());
    }

    @Transactional
    @DisplayName("회원 정보 수정")
    @Test
    void updateInfo() {
        // given
        Member member = new Member("greenus@naver.com", "name", SocialType.GOOGLE, "token");
        memberRepository.save(member);
        MemberRequest request = new MemberRequest("greenus", "178-70", "010-0000-0000", "gimpo");
        // when
        MemberResponse memberResponse = memberService.updateInfo(member.getId(), request);

        // then
        assertAll(
                () -> assertThat(memberResponse.getNickname()).isEqualTo("greenus"),
                () -> assertThat(memberResponse.getAddress()).isEqualTo("178-70"),
                () -> assertThat(memberResponse.getPhoneNum()).isEqualTo("010-0000-0000"),
                () -> assertThat(memberResponse.getInterestArea()).isEqualTo("gimpo")
        );
    }

}