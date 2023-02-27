package com.greenUs.server.product.controller;


import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.product.dto.respond.GetInfoDto;
import com.greenUs.server.product.dto.respond.GetProductDetailDto;
import com.greenUs.server.product.dto.respond.GetProductDto;
import com.greenUs.server.product.dto.respond.GetRecommendDto;
import com.greenUs.server.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductQueryController {

    private static final int DEFAULT_PAGE_SIZE =10;
    private static final String DEFAULT_PAGE_SORT = "viewCount";
    private final ProductQueryService productQueryService;

    @GetMapping("/products/{productId}/recommend")
    public List<GetRecommendDto> getRecommends(@PathVariable("productId")Long productId, @AuthenticationPrincipal LoginMember loginMember){
        return productQueryService.getRecommends(productId,loginMember.getId());
    }
    @GetMapping("/products")
    public Page<GetProductDto> getHomeProducts(
            @PageableDefault(size = DEFAULT_PAGE_SIZE,sort = DEFAULT_PAGE_SORT,direction = Sort.Direction.DESC) Pageable pageable){
        return productQueryService.getProducts(pageable);
    }

    @GetMapping("/products/{categoryName}")
    public Page<GetProductDto> getProductByCategory(
            @PathVariable("categoryName") String categoryName,
            @PageableDefault(size = DEFAULT_PAGE_SIZE,sort = DEFAULT_PAGE_SORT,direction = Sort.Direction.DESC) Pageable pageable){
        return productQueryService.getProductByCategory(categoryName,pageable);
    }

    @GetMapping("/products/{productId}")
    public GetProductDetailDto getProduct(@PathVariable("productId") Long productId){
        return productQueryService.getProduct(productId);
    }
    @GetMapping("/products/{productId}/infoCount")
    public GetInfoDto getProductInfoCount(@PathVariable("productId") Long productId){
        return productQueryService.getInfoNavigation(productId);
    }
}
