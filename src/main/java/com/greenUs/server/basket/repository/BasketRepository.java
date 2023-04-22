package com.greenUs.server.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenUs.server.basket.domain.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
