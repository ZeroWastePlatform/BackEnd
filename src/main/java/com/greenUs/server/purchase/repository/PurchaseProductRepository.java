package com.greenUs.server.purchase.repository;


import com.greenUs.server.member.domain.Member;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct,Long> {

    List<PurchaseProduct> findAllByMemberId(Long id);
}
