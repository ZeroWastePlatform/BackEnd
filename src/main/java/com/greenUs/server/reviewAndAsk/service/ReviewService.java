package com.greenUs.server.reviewAndAsk.service;

import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.domain.Review;
import com.greenUs.server.reviewAndAsk.dto.response.AskResponse;
import com.greenUs.server.reviewAndAsk.dto.response.ReviewResponse;
import com.greenUs.server.reviewAndAsk.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final int MAX_REVIEWS_COUNT = 5;

    private final ReviewRepository reviewRepository;

    public Page<ReviewResponse> getReviewByProductId(Long id, int page) {
        PageRequest pageRequest = PageRequest.of(page, MAX_REVIEWS_COUNT, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> reviews = reviewRepository.findByProductId(id, pageRequest);
        return transformAsks(reviews);
    }

    private Page<ReviewResponse> transformAsks(Page<Review> reviews) {
        return reviews.map(review ->
                ReviewResponse
                        .builder()
                        .id(review.getId())
                        .photoUrl(review.getPhotoUrl())
                        .content(review.getContent())
                        .rate(review.getRate())
                        .likedCount(review.getLikedCount())
                        .build());
    }
}
