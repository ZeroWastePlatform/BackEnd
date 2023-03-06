package com.greenUs.server.reviewAndAsk.service;

import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.dto.AskDto;
import com.greenUs.server.reviewAndAsk.dto.GetAskDetailDto;
import com.greenUs.server.reviewAndAsk.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public JSONObject getAsk(LoginMember loginMember,Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException());
        List<Ask> asks = product.getAsks();
        int totalElement = asks.size();
        List<GetAskDetailDto> getAskDetailDtos = new ArrayList<>();
        for(Ask ask :asks){
            GetAskDetailDto getAskDetailDto = GetAskDetailDto.builder()
                    .title(ask.getTitle())
                    .category(ask.getCategory())
                    .answer(ask.getAnswer())
                    .nickName(ask.getMember().getNickname())
                    .content(ask.getContent())
                    .secret(ask.isSecret())
                    .date(ask.getCreatedAt())
                    .build();
            getAskDetailDtos.add(getAskDetailDto);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalElement",totalElement);
        jsonObject.put("content",getAskDetailDtos);
        return jsonObject;
    }
}
