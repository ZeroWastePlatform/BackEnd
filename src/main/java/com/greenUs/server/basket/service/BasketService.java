package com.greenUs.server.basket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.basket.exception.NotEqualMemberAndBasketMember;
import com.greenUs.server.basket.exception.NotFoundBasketException;
import com.greenUs.server.basket.repository.BasketRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.post.exception.NotEqualMemberAndPostMember;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketService {

	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final BasketRepository basketRepository;

	public void createBasketProduct(Long memberId, Long productId) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

		Basket basket = Basket.builder()
			.member(member)
			.product(product)
			.build();

		basketRepository.save(basket);
	}

	public void deleteBasketProduct(Long memberId, Long id) {

		memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Basket basket = basketRepository.findById(id).orElseThrow(NotFoundBasketException::new);

		if (!basket.getMember().getId().equals(memberId))
			throw new NotEqualMemberAndBasketMember();

		basketRepository.delete(basket);
	}
}
