package com.greenUs.server.reviewAndAsk.repository;

import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r join r.product")
    Page<Review> findByProductId(Long id, Pageable pageable);
}
