package com.greenUs.server.member.service;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        return new MemberResponse(member);
    }

    @Transactional
    public MemberResponse updateInfo(Long id, MemberRequest memberRequest) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        member.changeInfo(
                memberRequest.getNickname(),
                memberRequest.getAddress(),
                memberRequest.getPhoneNum(),
                memberRequest.getInterestArea());
        return new MemberResponse(memberRepository.save(member));
    }
}