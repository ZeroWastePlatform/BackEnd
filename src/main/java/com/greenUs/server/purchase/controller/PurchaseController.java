package com.greenUs.server.purchase.controller;

import com.greenUs.server.purchase.dto.request.PurchaseRequest;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    // 주문 하기
    @PostMapping()
    public ResponseEntity<PurchaseResponse> createPurchase(
            @Valid @RequestBody PurchaseRequest purchaseRequest
            ) {
        PurchaseResponse purchaseResponse = purchaseService.createPurchase(purchaseRequest);
        return ResponseEntity.ok(purchaseResponse);
    }

    // 전체 주문 조회
    @GetMapping()
    public ResponseEntity<Page<PurchaseResponse>> getPurchaseList() {
        Page<PurchaseResponse> purchaseList = purchaseService.getList();
        return ResponseEntity.ok(purchaseList);
    }

    // 주문 하나만 조회
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchase(
            @PathVariable long id
    ) {
        PurchaseResponse purchaseResponse = purchaseService.getById(id);
        return ResponseEntity.ok(purchaseResponse);
    }

    // 주문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable long id
    ) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok("주문이 삭제 되었습니다.");
    }
}
