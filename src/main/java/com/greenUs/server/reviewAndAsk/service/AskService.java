package com.greenUs.server.reviewAndAsk.service;

import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.dto.AskDto;
import com.greenUs.server.reviewAndAsk.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AskService {
    private ProductRepository productRepository;
    private MemberRepository memberRepository;
    private AskRepository askRepository;
    public Long addAsk(LoginMember loginMember, Long productId, AskDto askDto){
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow(NotFoundMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException());
        Ask ask = Ask.builder()
                .answer("")
                .content(askDto.getContent())
                .member(member)
                .product(product)
                .secret(askDto.isSecret())
                .title(askDto.getTitle())

                .build();
        askRepository.save(ask);

        return ask.getId();
    }
}
