package com.greenUs.server.product.service;

import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.request.CreateProductDto;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;

    public Long createProduct(CreateProductDto createProductDto) {
        Product product = Product.builder()
                .title(createProductDto.getTitle())
                .description(createProductDto.getDescription())
                .image(createProductDto.getImage())
                .brand(createProductDto.getBrand())
                .viewCount(0)
                .price(createProductDto.getPrice())
                .build();
        productRepository.save(product);
        return product.getId();
    }
}
