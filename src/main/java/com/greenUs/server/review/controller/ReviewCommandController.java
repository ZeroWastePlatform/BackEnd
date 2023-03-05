package com.greenUs.server.review.controller;


import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.review.domain.Review;
import com.greenUs.server.review.dto.ReviewDto;
import com.greenUs.server.review.service.ReviewCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewCommandController {
    private ReviewCommandService reviewCommandService;

    @PostMapping(value ="products/{productId}/reviews")
    public Long addReview(@AuthenticationPrincipal LoginMember loginMember, @PathVariable("productId") Long productId, @RequestBody ReviewDto reviewDto){
        return reviewCommandService.addReview(loginMember,productId,reviewDto);
    }
}
