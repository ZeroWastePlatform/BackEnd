package com.greenUs.server.member.service;

import com.greenUs.server.coupon.repository.CouponRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository;
    private final CouponRepository couponRepository;

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        return new MemberResponse(member);
    }

    public MyPagePurchaseResponse getMyPagePurchase(MemberResponse member, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PurchaseResponse> purchaseResponses = purchaseRepository.findByMemberId(member.getId(), pageRequest);
        return new MyPagePurchaseResponse(
                member.getName(),
                member.getNickname(),
                member.getLevel(),
                0, // 찜은 추후 구현
                member.getPoint(),
                couponRepository.findCountByMemberId(member.getId()),
                purchaseResponses
        );
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
