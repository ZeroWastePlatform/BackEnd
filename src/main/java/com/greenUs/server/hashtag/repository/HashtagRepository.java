package com.greenUs.server.hashtag.repository;

import java.util.List;
import java.util.Optional;

import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.hashtag.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByPostIdAndKeywordId(Long postId, Long keywordId);

	List<Hashtag> findAllByPostId(Long postId);
}
