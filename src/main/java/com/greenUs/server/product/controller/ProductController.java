package com.greenUs.server.product.controller;

import com.greenUs.server.product.dto.request.ProductsRequest;
import com.greenUs.server.product.dto.response.ProductsResponse;
import com.greenUs.server.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "상품", description = "상품 API")
@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 전체 조회", description = "상품 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 전체 조회 성공", content = @Content(schema = @Schema(implementation = ProductsResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ProductsResponse>> getProducts(
            @Valid ProductsRequest productsRequest
    ){
        Page<ProductsResponse> response = productService.getProducts(productsRequest);
        return ResponseEntity.ok(response);
    }

}
