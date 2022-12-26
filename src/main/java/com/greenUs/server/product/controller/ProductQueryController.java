package com.greenUs.server.product.controller;


import com.greenUs.server.product.dto.ProductForm;
import com.greenUs.server.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductQueryController {

    private static final int DEFAULT_PAGE_SIZE =10;
    private static final String DEFAULT_PAGE_SORT = "viewCount";
    private final ProductQueryService productQueryService;

    @GetMapping("/products")
    public Page<ProductForm> getHomeProducts(
            @PageableDefault(size = DEFAULT_PAGE_SIZE,sort = DEFAULT_PAGE_SORT,direction = Sort.Direction.DESC) Pageable pageable){
        return productQueryService.getProducts(pageable);
    }

    @GetMapping("/products/{categoryName}")
    public Page<ProductForm> getProductByCategory(
            @PathVariable("categoryName") String categoryName,
            @PageableDefault(size = DEFAULT_PAGE_SIZE,sort = DEFAULT_PAGE_SORT,direction = Sort.Direction.DESC) Pageable pageable){
        return productQueryService.getProductByCategory(categoryName,pageable);
    }


}
