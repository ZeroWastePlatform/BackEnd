package com.greenUs.server.basket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.member.domain.Member;

public interface BasketRepository extends JpaRepository<Basket, Long> {

	Page<Basket> findByMember(Member Member, Pageable pageable);
}
