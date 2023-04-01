package com.greenUs.server.basket.service;


import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.basket.dto.BasketDto;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    public Long putProductToBasket(LoginMember loginMember, BasketDto basketDto){
        return null;
    }
}
