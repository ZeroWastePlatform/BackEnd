package com.greenUs.server.basket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.basket.dto.BasketResponse;
import com.greenUs.server.basket.exception.NotEqualMemberAndBasketMember;
import com.greenUs.server.basket.exception.NotFoundBasketException;
import com.greenUs.server.basket.repository.BasketRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.domain.ProductOption;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.repository.ProductOptionRepository;
import com.greenUs.server.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {

	private static final int MAX_BASKET_COUNT = 10;
	private final String orderCriteria = "createdAt";

	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final BasketRepository basketRepository;
	private final ProductOptionRepository productOptionRepository;

	public Page<BasketResponse> getBasketProductLists(Long memberId, Integer page) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

		PageRequest pageRequest = PageRequest.of(page, MAX_BASKET_COUNT, Sort.by(Sort.Direction.ASC, orderCriteria));

		Page<Basket> baskets = basketRepository.findByMember(member, pageRequest);

		return transformBaskets(baskets);
	}

	@Transactional
	public void createBasketProduct(Long memberId, Long productId) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

		Basket basket = Basket.builder()
			.member(member)
			.product(product)
			.build();

		basketRepository.save(basket);
	}

	@Transactional
	public void deleteBasketProduct(Long memberId, Long id) {

		memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Basket basket = basketRepository.findById(id).orElseThrow(NotFoundBasketException::new);

		if (!basket.getMember().getId().equals(memberId))
			throw new NotEqualMemberAndBasketMember();

		basketRepository.delete(basket);
	}

	/**
	 * Page<Basket> -> Page<BasketResponse> 로 변환
	 */
	private Page<BasketResponse> transformBaskets(Page<Basket> baskets) {

		return baskets.map(basket ->
			BasketResponse
				.builder()
				.basketId(basket.getId())
				.productId(basket.getProduct().getId())
				.thumbnail(basket.getProduct().getThumbnail())
				.brand(basket.getProduct().getBrand())
				.title(basket.getProduct().getTitle())
				.require(addRequireOption(basket.getProduct()))
				.bonus(addBonusOption(basket.getProduct()))
				.price(basket.getProduct().getPrice())
				.deliveryFee(basket.getProduct().getDeliveryFee())
				.discountRate(basket.getProduct().getDiscountRate())
				.stock(basket.getProduct().getStock())
				.build());
	}

	/**
	 * 필수 옵션 추가
	 */
	private List<Map<String, Integer>> addRequireOption(Product product) {

		List<Map<String, Integer>> option = new ArrayList<>();

		for (ProductOption productOption : productOptionRepository.findByProduct(product)) {

			Map<String, Integer> require = new HashMap<>();

			if (productOption.getIsRequire()) {
				require.put(productOption.getTitle(), productOption.getPrice());
				option.add(require);
			}
		}
		return option;
	}

	/**
	 * 선택 옵션 추가
	 */
	private List<Map<String, Integer>> addBonusOption(Product product) {

		List<Map<String, Integer>> option = new ArrayList<>();

		for (ProductOption productOption : productOptionRepository.findByProduct(product)) {

			Map<String, Integer> bonus = new HashMap<>();

			if (!productOption.getIsRequire()) {
				bonus.put(productOption.getTitle(), productOption.getPrice());
				option.add(bonus);
			}
		}
		return option;
	}
}
