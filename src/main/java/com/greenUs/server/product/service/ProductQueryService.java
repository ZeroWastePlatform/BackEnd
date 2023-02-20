package com.greenUs.server.product.service;


import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.respond.GetProductDetailDto;
import com.greenUs.server.product.dto.respond.GetProductDto;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    //Mapper 보류(논의) 그냥 builder()?
    private final ProductRepository productRepository;

    public Page<GetProductDto> getProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        Page<GetProductDto> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }

    public Page<GetProductDto> getProductByCategory(String categoryName, Pageable pageable){
        Page<Product> products = productRepository.findAllByCategory(categoryName,pageable);
        Page<GetProductDto> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }

    public GetProductDetailDto getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException());
        List<String> thumbnails = new ArrayList<>();
        thumbnails.add(product.getImage());
        GetProductDetailDto getProductDetailDto = GetProductDetailDto.builder()
                .price(product.getPrice())
                .title(product.getTitle())
                .summary(product.getDescription())
                .liked(product.getLikeCount())
                .badges("NEW")
                .thumbnail(thumbnails)
                .category(product.getCategory().toString())
                .build();
        return getProductDetailDto;
    }


    private GetProductDto makeProductForm(Product product) {
        GetProductDto getProductDto = GetProductDto.builder()
                .id(product.getId())
                .image(product.getImage())
                .brand(product.getBrand())
                .price(product.getPrice())
                .title(product.getTitle())
                .build();
        return getProductDto;
    }
}
