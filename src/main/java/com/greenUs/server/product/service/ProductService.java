package com.greenUs.server.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.domain.ProductLike;
import com.greenUs.server.product.domain.ProductOption;
import com.greenUs.server.product.dto.Order;
import com.greenUs.server.product.dto.request.ProductsRequest;
import com.greenUs.server.product.dto.response.ProductDetailResponse;
import com.greenUs.server.product.dto.response.ProductsResponse;
import com.greenUs.server.product.exception.LikeDuplicateException;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.repository.ProductLikeRepository;
import com.greenUs.server.product.repository.ProductOptionRepository;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.product.repository.ProductRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

	private static final int MAX_PRODUCTS_COUNT = 9;
	private static final int BEST_CATEGORY_COUNT = 6;

	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final ProductRepositoryCustom productRepositoryCustom;
	private final ProductLikeRepository productLikeRepository;
	private final ProductOptionRepository productOptionRepository;

	public Page<ProductsResponse> getProducts(ProductsRequest productsRequest) {

		// 베스트 TOP 6 케이스 따로 관리
		if (productsRequest.getOrder().equals(Order.TOP6)) {
			PageRequest pageRequest = PageRequest.of(productsRequest.getPage(), BEST_CATEGORY_COUNT,
				Sort.by(Sort.Direction.DESC, Order.POPULARITY.getName()));
			return transformProducts(
				productRepository.findTop6ByCategoryDesc(productsRequest.getCategory(), pageRequest));
		}

		// 정렬조건을 반환해주는 함수
		PageRequest pageRequest = getPageRequest(productsRequest);

		// 전체 검색일 경우
		if (productsRequest.getCategory() == null) {
			return transformProducts(productRepository.findAll(pageRequest));
		}

		// 카테고리별 검색 + 필터 적용
		Page<ProductsResponse> productsResponses = transformProducts(
			productRepositoryCustom.findWithSearchCondition(
				productsRequest.getCategory(),
				productsRequest.getBrand(),
				productsRequest.getPrice(),
				productsRequest.getProductStatus(),
				pageRequest
			)
		);

		return productsResponses;
	}

	public ProductDetailResponse getProductDetail(Long id) {

		Product product = productRepository.findById(id).orElseThrow(NotFoundProductException::new);

		return addProductDetailOption(transformProductDetail(product), product);
	}

	/**
	 * PageRequest 생성 (페이지번호, 페이지당 상품 수, 정렬 기준)
	 */
	private PageRequest getPageRequest(ProductsRequest productsRequest) {

		Order order = productsRequest.getOrder();
		// 오름차순 (낮은가격순)
		if (order == Order.LOW_PRICE)
			return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT,
				Sort.by(Sort.Direction.ASC, order.getName()));

		// 내림차순 (높은가격순, 인기순, 최신날짜)
		return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT,
			Sort.by(Sort.Direction.DESC, order.getName()));
	}

	/**
	 * Page<Product> -> Page<ProductsResponse> 로 변환
	 */
	private Page<ProductsResponse> transformProducts(Page<Product> products) {

		return products.map(product ->
			ProductsResponse
				.builder()
				.id(product.getId())
				.category(product.getCategory())
				.discountRate(product.getDiscountRate())
				.badges(product.getBadges())
				.title(product.getTitle())
				.description(product.getDescription())
				.brand(product.getBrand())
				.viewCount(product.getViewCount())
				.stock(product.getStock())
				.price(product.getPrice())
				.deliveryFee(product.getDeliveryFee())
				.likeCount(product.getLikeCount())
				.reviewCount(product.getReviewCount())
				.thumbnail(product.getThumbnail())
				.build());
	}

	/**
	 * Product -> ProductDetailResponse 로 변환
	 */
	private ProductDetailResponse transformProductDetail(Product product) {

		return ProductDetailResponse
			.builder()
			.id(product.getId())
			.category(product.getCategory())
			.discountRate(product.getDiscountRate())
			.badges(product.getBadges())
			.title(product.getTitle())
			.description(product.getDescription())
			.brand(product.getBrand())
			.viewCount(product.getViewCount())
			.stock(product.getStock())
			.price(product.getPrice())
			.deliveryFee(product.getDeliveryFee())
			.likeCount(product.getLikeCount())
			.reviewCount(product.getReviewCount())
			.thumbnail(product.getThumbnail())
			.require(new ArrayList<>())
			.bonus(new ArrayList<>())
			.build();
	}

	/**
	 * ProductDetailResponse 옵션 상품 추가
	 */
	private ProductDetailResponse addProductDetailOption(ProductDetailResponse productDetailResponse, Product product) {

		for (ProductOption productOption : productOptionRepository.findByProduct(product)) {

			Map<String, Integer> option = new HashMap<>();

			if (productOption.getIsRequire()) {
				option.put(productOption.getTitle(), productOption.getPrice());
				productDetailResponse.getRequire().add(option);
				continue;
			}
			option.put(productOption.getTitle(), productOption.getPrice());
			productDetailResponse.getBonus().add(option);
		}

		return productDetailResponse;
	}

	@Transactional
	public void likeProduct(Long memberId, Long productId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

		// 이미 좋아요가 눌러져 있다면
		if (productLikeRepository.findByMemberAndProduct(member, product).isPresent()) {
			throw new LikeDuplicateException();
		}

		ProductLike productLike = ProductLike.builder()
			.member(member)
			.product(product)
			.build();

		updateLikeCnt(true, productId);
		productLikeRepository.save(productLike);
	}

	@Transactional
	public void likeCancelProduct(Long memberId, Long productId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
		ProductLike productLike = productLikeRepository.findByMemberAndProduct(member, product)
			.orElseThrow(LikeDuplicateException::new);

		updateLikeCnt(false, productId);
		productLikeRepository.delete(productLike);
	}

	@Transactional
	public void updateLikeCnt(boolean isLikeAdd, Long productId) {

		// 좋아요 누른 경우
		if (isLikeAdd) {
			productRepository.addLikeCount(productId);
		}
		// 좋아요 취소한 경우
		else {
			productRepository.minusLikeCount(productId);
		}
	}
}
