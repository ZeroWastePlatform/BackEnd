package com.greenUs.server.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.domain.ProductOption;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	List<ProductOption> findByProduct(Product product);
}
