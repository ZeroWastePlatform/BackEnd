package com.greenUs.server.basket.controller;


import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.basket.dto.BasketDto;
import com.greenUs.server.basket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;
    @PostMapping(value ="/put")
    public Long putProductToBasket(@AuthenticationPrincipal LoginMember loginMember, @RequestBody BasketDto basketDto){
        return basketService.putProductToBasket(loginMember,basketDto);
    }
}
