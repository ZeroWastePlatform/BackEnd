package com.greenUs.server.post.repository;

import java.util.List;

import com.greenUs.server.post.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	// 게시판 종류(kind)를 파라미터로 받아 해당 게시판의 내용물을 최신순으로 가져오기
	@Query("select post from Post post where post.kind = :kind order by post.createdAt desc")
	List<Post> findAllKindDesc(@Param("kind") Integer kind);
}
