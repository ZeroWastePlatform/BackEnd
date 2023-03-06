package com.greenUs.server.reviewAndAsk.controller;


import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.reviewAndAsk.dto.ReviewDto;
import com.greenUs.server.reviewAndAsk.service.ReviewCommandService;
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
