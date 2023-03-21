package com.greenUs.server.product.service;

import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.Order;
import com.greenUs.server.product.dto.request.ProductsRequest;
import com.greenUs.server.product.dto.response.ProductDetailResponse;
import com.greenUs.server.product.dto.response.ProductsResponse;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.product.repository.ProductRepositoryCustom;
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
    private final ProductRepositoryCustom productRepositoryCustom;


    public Page<ProductsResponse> getProducts (ProductsRequest productsRequest) {

        Page<ProductsResponse> productsResponses = null;

        // 정렬조건을 반환해주는 함수
        PageRequest pageRequest = getPageRequest(productsRequest);

        // 전체 검색일 경우
        if (productsRequest.getCategory() == null) {
            productsResponses = transformProducts(productRepository.findAll(pageRequest));
        }

        // 검색 및 필터 적용
        else {
            productsResponses = transformProducts(
                    productRepositoryCustom.findWithSearchCondition(
                            productsRequest.getCategory(),
                            productsRequest.getBrand(),
                            productsRequest.getPrice(),
                            productsRequest.getProductStatus(),
                            pageRequest
                    )
            );
        }

        return productsResponses;
    }

    public ProductDetailResponse getProductDetail(Long id) {

        Product product = productRepository.findById(id).orElseThrow(NotFoundProductException::new);

        return transformProductDetail(product);
    }

    /**
     * PageRequest 생성 (페이지번호, 페이지당 상품 수, 정렬 기준)
     */
    private PageRequest getPageRequest(ProductsRequest productsRequest) {

        Order order = productsRequest.getOrder();
        // 오름차순 (낮은가격순)
        if (order == Order.LOW_PRICE)
            return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT, Sort.by(Sort.Direction.ASC, order.getName()));

        // 내림차순 (높은가격순, 인기순, 최신날짜)
        return PageRequest.of(productsRequest.getPage(), MAX_PRODUCTS_COUNT, Sort.by(Sort.Direction.DESC, order.getName()));
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
                .build();
    }
}
