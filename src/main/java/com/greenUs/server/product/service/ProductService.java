package com.greenUs.server.product.service;

import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.Order;
import com.greenUs.server.product.dto.request.ProductsRequest;
import com.greenUs.server.product.dto.response.ProductsResponse;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int MAX_PRODUCTS_COUNT = 9;

    private final ProductRepository productRepository;

    public Page<ProductsResponse> getProducts (ProductsRequest productsRequest) {

        Page<ProductsResponse> productsResponses = null;

        // 정렬조건을 반환해주는 함수
        PageRequest pageRequest = getPageRequest(productsRequest);

        // 전체 검색일 경우
        if (productsRequest.getCategory() == null) {
            productsResponses = transformProducts(productRepository.findAll(pageRequest));
        }

        // 특정 카테고리 검색일 경우
        else {
            productsResponses = transformProducts(productRepository.findByCategory(productsRequest.getCategory(), pageRequest));
        }

        return productsResponses;
    }

    private PageRequest getPageRequest(ProductsRequest productsRequest) {

        Order order = productsRequest.getOrder();
        // 오름차순 (낮은가격순)
        if (order == Order.LOW_PRICE)
            return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT, Sort.by(Sort.Direction.ASC, order.getName()));

        // 내림차순 (높은가격순, 인기순, 최신날짜)
        return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT, Sort.by(Sort.Direction.DESC, order.getName()));
    }

    // 전체 검색: Product -> DTO 변환
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
                        .productStatus(product.getProductStatus())
                        .viewCount(product.getViewCount())
                        .price(product.getPrice())
                        .deliveryFee(product.getDeliveryFee())
                        .likeCount(product.getLikeCount())
                        .reviewCount(product.getReviewCount())
                        .build());
    }
}
