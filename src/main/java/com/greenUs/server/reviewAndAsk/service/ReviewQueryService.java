package com.greenUs.server.reviewAndAsk.service;

import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.reviewAndAsk.domain.Review;
import com.greenUs.server.reviewAndAsk.dto.GetReviewDetailDto;
import com.greenUs.server.reviewAndAsk.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    public JSONObject getReviews(Long productId, LoginMember loginMember,String sorted){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException());
        List<Review> reviews = product.getReviews();
        int total = reviews.size();
        double rate =0;
        List<Integer> rates = new ArrayList<>();
        for(int i=0;i<5;i++){
            rates.add(0);
        }
        for(int i=0;i<reviews.size();i++){
            rate+=reviews.get(i).getRate();
            rates.set(reviews.get(i).getRate()-1, rates.get(reviews.get(i).getRate()-1)+1);
        }
        rate /= reviews.size();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("avgRate",rate);
        jsonObject.put("total",total);
        jsonObject.put("rates",rates);
        List<GetReviewDetailDto> getReviewDetailDtos = new ArrayList<>();
        for(Review review : reviews){
            GetReviewDetailDto getReviewDetailDto = GetReviewDetailDto.builder()
                    .date(review.getCreatedAt())
                    .content(review.getContent())
                    .likedCount(review.getLikedCount())
                    .nickname(review.getMember().getNickname())
                    .photo(review.getPhoto())
                    .rate(review.getRate())
                    .avatar(null)
                    .liked(false)
                    .build();
            getReviewDetailDtos.add(getReviewDetailDto);
        }
        if(sorted.equals("new")){
            getReviewDetailDtos.stream().sorted(Comparator.comparing(GetReviewDetailDto::getLikedCount).reversed());
        }
        jsonObject.put("content",getReviewDetailDtos);
        return jsonObject;
    }

}
