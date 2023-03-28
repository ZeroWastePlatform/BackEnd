package com.greenUs.server.purchase.repository;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select p from Purchase p\n"
        + "where p.member.id = :id")
    Page<PurchaseResponse> findByMemberId(Long id, Pageable pageable);

    List<Purchase> findByMember(Member member);
}
