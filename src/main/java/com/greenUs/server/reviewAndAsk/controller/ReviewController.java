package com.greenUs.server.reviewAndAsk.controller;

import com.greenUs.server.reviewAndAsk.dto.response.AskResponse;
import com.greenUs.server.reviewAndAsk.dto.response.ReviewResponse;
import com.greenUs.server.reviewAndAsk.service.ReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByProductId(
            @Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long id,
            @Parameter(description = "현재 문 페이지 값", in = ParameterIn.QUERY) @RequestParam(required = false, defaultValue = "0") Integer page) {
        {
            Page<ReviewResponse> response = reviewService.getReviewByProductId(id, page);
            return ResponseEntity.ok(response);
        }
    }
}
