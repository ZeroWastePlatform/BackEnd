package com.greenUs.server.order.repository;

import com.greenUs.server.order.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Purchase, Long> {
}
