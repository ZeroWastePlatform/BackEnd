package com.greenUs.server.purchase.repository;

import com.greenUs.server.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
