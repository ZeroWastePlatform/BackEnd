package com.greenUs.server.product.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.dto.request.ProductsRequest;
import com.greenUs.server.product.dto.response.ProductDetailResponse;
import com.greenUs.server.product.dto.response.ProductsResponse;
import com.greenUs.server.product.exception.LikeDuplicateException;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "상품", description = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 전체 조회", description = "상품 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 전체 조회 성공", content = @Content(schema = @Schema(implementation = ProductsResponse.class))),
            @ApiResponse(responseCode = "400", description = "파라미터 값 오류", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ProductsResponse>> getProducts(
            @Valid ProductsRequest productsRequest
    ){
        Page<ProductsResponse> response = productService.getProducts(productsRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공", content = @Content(schema = @Schema(implementation = ProductsResponse.class))),
            @ApiResponse(responseCode = "400", description = "상품 번호가 DB 에 없음", content = @Content(schema = @Schema(implementation = NotFoundProductException.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long id) {
        {
            ProductDetailResponse response = productService.getProductDetail(id);
            return ResponseEntity.ok(response);
        }
    }

    @Operation(summary = "상품 좋아요", description = "상품 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 좋아요 성공"),
            @ApiResponse(responseCode = "400", description = "상품 번호가 DB 에 없음", content = @Content(schema = @Schema(implementation = NotFoundProductException.class))),
            @ApiResponse(responseCode = "409", description = "좋아요가 중복되었음", content = @Content(schema = @Schema(implementation = LikeDuplicateException.class)))
    })
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeProduct(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long id) {
        {
            productService.likeProduct(loginMember.getId(), id);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "상품 좋아요 취소", description = "상품 좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 좋아요 취소 성공"),
            @ApiResponse(responseCode = "400", description = "상품 번호가 DB 에 없음", content = @Content(schema = @Schema(implementation = NotFoundProductException.class))),
            @ApiResponse(responseCode = "409", description = "좋아요가 중복되었음", content = @Content(schema = @Schema(implementation = LikeDuplicateException.class)))
    })
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> likeCancelProduct(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long id) {
        {
            productService.likeCancelProduct(loginMember.getId(), id);
            return ResponseEntity.ok().build();
        }
    }
}
