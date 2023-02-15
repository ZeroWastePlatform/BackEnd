package com.greenUs.server.comment.repository;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// 게시판 번호(postId)와 pageRequest를 파라미터로 받아 댓글 내용을 가져오기
	@Query("select c from Comment c where c.post.id = :postId")
	Page<Comment> findByPostId(Long postId, Pageable pageable);
}
