package com.greenUs.server.review.controller;



import com.greenUs.server.review.dto.GetReviewDto;
import com.greenUs.server.review.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewQueryController {
    private final ReviewQueryService reviewQueryService;
    private static final String DEFAULT_REVIEW_SORT = "helpCnt";
    @GetMapping(value ="products/{productId}/reviews")
    public Page<GetReviewDto> getReviews(
            @PathVariable("productId") Long productId,
            @PageableDefault(size = 5,sort = DEFAULT_REVIEW_SORT,direction = Sort.Direction.DESC)Pageable pageable){
        return reviewQueryService.getReviews(productId,pageable);
    }
}
