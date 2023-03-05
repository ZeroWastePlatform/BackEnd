package com.greenUs.server.review.service;

import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.review.domain.Review;
import com.greenUs.server.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandService {
    private ProductRepository productRepository;
    private MemberRepository memberRepository;
    public Long addReview(LoginMember loginMember, Long productId, ReviewDto reviewDto){
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow(NotFoundMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException());
        Review review = Review.builder()
                .content(reviewDto.getContent())
                .product(product)
                .member(member)
                .photo(reviewDto.getPhoto())
                .likedCount(0)
                .rate(reviewDto.getRate())
                .build();
        return review.getId();
    }
}
