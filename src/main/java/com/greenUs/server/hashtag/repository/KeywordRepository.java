package com.greenUs.server.hashtag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenUs.server.hashtag.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

	Optional<Keyword> findByContent(String keywordContent);

	List<Keyword> findTop5ByOrderByCountDesc();
}
