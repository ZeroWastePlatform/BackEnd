package com.greenUs.server.reviewAndAsk.repository;

import com.greenUs.server.reviewAndAsk.domain.Ask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AskRepository extends JpaRepository<Ask,Long> {

    @Query("select a from Ask a join a.product")
    Page<Ask> findByProductId(Long id, Pageable pageable);
}
