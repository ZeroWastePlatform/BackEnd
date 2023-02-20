package com.greenUs.server.product.controller;


import com.greenUs.server.product.dto.request.CreateProductDto;
import com.greenUs.server.product.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductCommandController {
    private final ProductCommandService productCommandService;

    @PostMapping(value ="product/create")
    public Long createProduct(@RequestBody CreateProductDto createProductDto){
        return productCommandService.createProduct(createProductDto);
    }


}
