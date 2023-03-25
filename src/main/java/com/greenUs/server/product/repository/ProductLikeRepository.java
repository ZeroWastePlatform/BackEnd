package com.greenUs.server.product.repository;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.domain.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    Optional<ProductLike> findByMemberAndProduct(Member member, Product product);
}
