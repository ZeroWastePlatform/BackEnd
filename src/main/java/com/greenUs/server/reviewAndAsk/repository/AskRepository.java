package com.greenUs.server.reviewAndAsk.repository;

import com.greenUs.server.reviewAndAsk.domain.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;

@Repository
public interface AskRepository extends JpaRepository<Ask,Long> {
}
