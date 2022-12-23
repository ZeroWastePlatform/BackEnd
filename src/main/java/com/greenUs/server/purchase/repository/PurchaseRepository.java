package com.greenUs.server.purchase.repository;

import com.greenUs.server.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
