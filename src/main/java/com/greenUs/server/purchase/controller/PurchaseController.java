package com.greenUs.server.purchase.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.purchase.dto.request.PurchaseRequest;
import com.greenUs.server.purchase.dto.response.PurchaseProductResponse;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "구매", description = "구매 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    // 주문 하기
    @PostMapping()
    public ResponseEntity<PurchaseResponse> createPurchase(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody PurchaseRequest purchaseRequest) {
        PurchaseResponse purchaseResponse = purchaseService.createPurchase(purchaseRequest, loginMember.getId());
        return ResponseEntity.ok(purchaseResponse);
    }

    // 전체 주문 조회
    @GetMapping()
    public ResponseEntity<Page<PurchaseProductResponse>> getPurchaseList(
            @Parameter(description = "내 주문 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember) {
        Page<PurchaseProductResponse> purchaseList = purchaseService.getList(loginMember.getId(), page);
        return ResponseEntity.ok(purchaseList);
    }

}
