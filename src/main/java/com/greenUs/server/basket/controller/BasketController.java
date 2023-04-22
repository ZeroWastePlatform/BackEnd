package com.greenUs.server.basket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.basket.service.BasketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
public class BasketController {

	private final BasketService basketService;

	// @GetMapping
	// public ResponseEntity<List<BasketDto>> getBasketProductLists(@AuthenticationPrincipal LoginMember loginMember) {
	//
	//     List<BasketDto> basketDtoList = basketService.getBasketLists(loginMember.getId());
	//
	//     return new ResponseEntity<>(basketDtoList, HttpStatus.OK);
	// }

	@PostMapping("/{productId}")
	public ResponseEntity createBasketProduct(
		@AuthenticationPrincipal LoginMember loginMember,
		@PathVariable Long productId) {

		basketService.createBasketProduct(loginMember.getId(), productId);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteBasketProduct(
	    @AuthenticationPrincipal LoginMember loginMember,
	    @PathVariable Long id){

		basketService.deleteBasketProduct(loginMember.getId(), id);

	    return new ResponseEntity<>(HttpStatus.OK);
	}
}
