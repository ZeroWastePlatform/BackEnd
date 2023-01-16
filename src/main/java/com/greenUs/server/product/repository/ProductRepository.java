package com.greenUs.server.product.repository;

import com.greenUs.server.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
//    @Query("select p from Product p order by p.viewCount DESC ")
//    List<Product> findAllByViewCount();
    @Query("select p from Product p where p.category =: category")
     Page<Product> findAllByCategory(@Param("category") String category,Pageable pageable);
}
