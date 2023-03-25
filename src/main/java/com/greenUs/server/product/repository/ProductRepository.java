package com.greenUs.server.product.repository;

import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 전체 조회
    Page<Product> findAll(Pageable pageable);

    // 카테고리별 조회
    @Query("select p from Product p where p.category = :category")
    Page<Product> findByCategory(Category category, Pageable pageable);

    // 베스트 카테고리 카테고리별 상위 6개 항목 반환
    @Query("select p from Product p where p.category = :category")
    Page<Product> findTop6ByCategoryDesc(Category category, Pageable pageable);

    // 좋아요 개수 증가
    @Modifying
    @Query("update Product p set p.likeCount = p.likeCount+1 where p.id = :id")
    void addLikeCount(Long id);

    // 좋아요 개수 감소
    @Modifying
    @Query("update Product p set p.likeCount = p.likeCount-1 where p.id = :id")
    void minusLikeCount(Long id);
}
