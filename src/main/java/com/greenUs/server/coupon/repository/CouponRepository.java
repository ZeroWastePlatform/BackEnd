package com.greenUs.server.coupon.repository;

import com.greenUs.server.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select count(c) from Coupon c \n"
        + "where c.member.id = :id")
    int findCountByMemberId(Long id);
}
