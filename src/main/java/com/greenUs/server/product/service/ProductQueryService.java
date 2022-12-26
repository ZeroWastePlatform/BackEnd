package com.greenUs.server.product.service;


import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;
}
