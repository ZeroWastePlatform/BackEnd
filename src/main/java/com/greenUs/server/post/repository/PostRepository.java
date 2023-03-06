package com.greenUs.server.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.greenUs.server.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// 게시판 종류(kind)와 pageRequest를 파라미터로 받아 게시판 상단에 추천수TOP2개, 나머지 최신순 정렬 (수정요망)
	Page<Post> findByKind(Integer kind, Pageable pageable);

	// 게시글 제목과 내용에 해당하는 단어가 있는 검색물을 반환
	Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

	// 내가 작성한 게시물 반환
	Page<Post> findByMemberId(Long memberId, Pageable pageable);

	// 해시태그 키워드 검색
	@Query("select distinct p from Post p\n"
		+ "inner join Hashtag h on p.id = h.post.id\n"
		+ "inner join Keyword k on h.keyword.id = k.id\n"
		+ "where k.content = :keyword")
	Page<Post> findByKeywordContaining(String keyword, Pageable pageable);

	// 게시판 조회시 조회수 증가
	@Modifying
	@Query("update Post post set post.viewCnt = post.viewCnt+1 where post.id = :id")
	void updateViewCnt(Long id);

	// 당일 인기 게시글 3개
	List<Post> findTop3ByCreatedAtBetweenOrderByRecommendCntDesc(LocalDateTime startDateTime, LocalDateTime endDateTime);

	// 인기 추천글 3개
	List<Post> findTop3ByKindOrderByRecommendCntDesc(Integer kind);
}
