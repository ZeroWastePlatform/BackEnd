package com.greenUs.server.review.controller;



import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.review.dto.GetReviewDto;
import com.greenUs.server.review.service.ReviewQueryService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
