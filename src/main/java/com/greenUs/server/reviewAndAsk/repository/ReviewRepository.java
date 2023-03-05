package com.greenUs.server.reviewAndAsk.repository;

import com.greenUs.server.reviewAndAsk.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
