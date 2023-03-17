package com.greenUs.server.product.repository;

import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 전체 조회
    Page<Product> findAll(Pageable pageable);

    // 카테고리별 조회
    @Query("select p from Product p where p.category = :category")
    Page<Product> findByCategory(Category category, Pageable pageable);

}
