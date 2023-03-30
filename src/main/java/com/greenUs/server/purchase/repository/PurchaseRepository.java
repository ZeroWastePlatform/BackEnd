package com.greenUs.server.purchase.repository;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByMember(Member member);
}
