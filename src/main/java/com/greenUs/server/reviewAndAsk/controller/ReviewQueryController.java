package com.greenUs.server.reviewAndAsk.controller;



import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.reviewAndAsk.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewQueryController {
    private final ReviewQueryService reviewQueryService;
    private static final String DEFAULT_REVIEW_SORT = "helpCnt";
    @GetMapping(value ="products/{productId}/reviews/liked")
    public JSONObject getReviewsLikedCountSorted(
            @PathVariable("productId") Long productId,
            @AuthenticationPrincipal LoginMember loginMember
            ){
        return reviewQueryService.getReviews(productId,loginMember,"liked");
    }
    @GetMapping(value ="products/{productId}/reviews/new")
    public JSONObject getReviewsNewSorted(
            @PathVariable("productId") Long productId,
            @AuthenticationPrincipal LoginMember loginMember
    ){
        return reviewQueryService.getReviews(productId,loginMember,"new");
    }
}
