package com.greenUs.server.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// 게시판 종류(kind)를 파라미터로 받아 해당 게시판의 내용물을 최신순으로 가져오기
	@Query("select post from Post post where post.kind = :kind order by post.createdAt desc")
	List<Post> findAllKindDesc(@Param("kind") Integer kind);

	// 닉네임과 게시글 번호를 받으면 닉네임으로 Member Entity에서 PK를 구한다음, PK를 가지고 작성한 글 목록을 찾아서 게시글 번호와 일치하는지 확인
	// 또는 Post Entity에 해당 닉네임을 만들어놓고 브라우저에서 닉네임과 게시글 번호 받으면 닉네임끼리 비교 후 삭제
}
