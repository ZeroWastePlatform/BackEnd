package com.greenUs.server.reviewAndAsk.controller;

import com.greenUs.server.product.dto.response.ProductDetailResponse;
import com.greenUs.server.reviewAndAsk.dto.response.AskResponse;
import com.greenUs.server.reviewAndAsk.service.AskService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asks")
public class AskController {

    private final AskService askService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<AskResponse>> getAsksByProductId(
            @Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long id,
            @Parameter(description = "현재 문 페이지 값", in = ParameterIn.QUERY) @RequestParam(required = false, defaultValue = "0") Integer page) {
        {
            Page<AskResponse> response = askService.getAskByProductId(id, page);
            return ResponseEntity.ok(response);
        }
    }
}
