package com.greenUs.server.basket.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.basket.dto.BasketResponse;
import com.greenUs.server.basket.service.BasketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "장바구니", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
public class BasketController {

	private final BasketService basketService;

	@Operation(summary = "장바구니 조회", description = "장바구니 조회할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "장바구니 조회 성공", content = @Content(schema = @Schema(implementation = BasketResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping
	public ResponseEntity<Page<BasketResponse>> getBasketProductLists(
		@Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
		@Parameter(description = "장바구니 페이지 값", in = ParameterIn.PATH) @RequestParam(defaultValue = "0", value = "page") Integer page) {

		Page<BasketResponse> responses = basketService.getBasketProductLists(loginMember.getId(), page);

		return new ResponseEntity<>(responses, HttpStatus.OK);
	}

	@Operation(summary = "장바구니 등록", description = "상품을 장바구니에 등록할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "상품 장바구니 등록"),
		@ApiResponse(responseCode = "404", description = "상품 정보가 DB에 없음", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping("/{productId}")
	public ResponseEntity createBasketProduct(
		@Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
		@Parameter(description = "상품 번호", in = ParameterIn.PATH) @PathVariable Long productId) {

		basketService.createBasketProduct(loginMember.getId(), productId);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "장바구니 삭제", description = "상품을 장바구니에서 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상품 장바구니 삭제"),
		@ApiResponse(responseCode = "404", description = "장바구니 정보가 DB에 없음", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@DeleteMapping("/{id}")
	public ResponseEntity deleteBasketProduct(
		@Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
		@Parameter(description = "장바구니 번호", in = ParameterIn.PATH) @PathVariable Long id) {

		basketService.deleteBasketProduct(loginMember.getId(), id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
