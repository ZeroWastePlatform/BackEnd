package com.greenUs.server.member.service;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.exception.InvalidMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse updateInfo(MemberRequest memberRequest) {
        Long id = memberRequest.getId();
        Member member = memberRepository.findById(id).orElseThrow(InvalidMemberException::new);

        // access token 이 Bearer 헤더에 들어올거고 이를 검사
        // 만약 만료되었다면 -> refresh 토큰 검사 -> 얘도 만료되었다면 새로 로그인
        //                                 -> 만료되지 않았으면 얘로 새로 발급
        // TODO: 위 과정을 서비스에서 추가하지 말고 AOP 방식으로 추가

        member.changeInfo(
                memberRequest.getNickname(),
                memberRequest.getAddress(),
                memberRequest.getPhoneNum(),
                memberRequest.getInterestArea());
        return new MemberResponse(memberRepository.save(member));
    }
}
