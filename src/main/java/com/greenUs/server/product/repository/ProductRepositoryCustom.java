package com.greenUs.server.product.repository;

import com.greenUs.server.product.domain.Brand;
import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.Price;
import com.greenUs.server.product.dto.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> findWithSearchCondition(
            Category category,
            Brand brand,
            Price price,
            ProductStatus productStatus,
            Pageable pageable);
}
