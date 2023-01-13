package com.greenUs.server.review.service;

import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.review.domain.Review;
import com.greenUs.server.review.dto.GetReviewDto;
import com.greenUs.server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    public Page<GetReviewDto> getReviews(Long productId, Pageable pageable){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException());
        List<GetReviewDto> getReviewDtos = product.getReviews().stream().map(
                review -> GetReviewDto.builder()
                        .starCnt(review.getStarCnt())
                        .image(review.getImage())
                        .description(review.getDescription())
                        .userName(review.getMember().getName())
                        .helpCnt(review.getHelpCnt())
                        .createAt(review.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());
        Page<GetReviewDto> reviewDtoPage = new PageImpl<>(getReviewDtos,pageable,getReviewDtos.size());
        return reviewDtoPage;
    }

}
