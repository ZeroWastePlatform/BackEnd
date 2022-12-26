package com.greenUs.server.product.service;


import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.ProductForm;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    //Mapper 보류(논의) 그냥 builder()?
    private final ProductRepository productRepository;

    @Transactional
    public Page<ProductForm> getHomeProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductForm> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }

    @Transactional
    public Page<ProductForm> getProductByCategory(String categoryName,Pageable pageable){
        Page<Product> products = productRepository.findAllByCategory(categoryName,pageable);
        Page<ProductForm> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }

    private ProductForm makeProductForm(Product product) {
        ProductForm productForm = ProductForm.builder()
                .id(product.getId())
                .image(product.getImage())
                .brand(product.getBrand())
                .price(product.getPrice())
                .title(product.getTitle())
                .build();
        return productForm;
    }
}
