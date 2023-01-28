package com.greenUs.server.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.greenUs.server.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// 게시판 종류(kind)와 pageRequest를 파라미터로 받아 게시판 종류의 내용물을 정렬 조건순으로 가져오기
	Page<Post> findByKind(Integer kind, Pageable pageable);

	// 제목 검색
	Page<Post> findByKindAndTitleContaining(Integer kind, String title, Pageable pageable);

	// 내용 검색
	Page<Post> findByKindAndContentContaining(Integer kind, String content, Pageable pageable);

	// 키워드 검색
	@Query("select distinct p from Post p\n"
		+ "inner join Hashtag h on p.id = h.post.id\n"
		+ "inner join Keyword k on h.keyword.id = k.id\n"
		+ "where k.content = :hashtag and p.kind = :kind")
	Page<Post> findByKindAndHashtagContaining(Integer kind, String hashtag, Pageable pageable);

	// 게시판 조회시 조회수 증가
	@Modifying
	@Query("update Post post set post.viewCnt = post.viewCnt+1 where post.id = :id")
	void updateViewCnt(Long id);

	// 닉네임과 게시글 번호를 받으면 닉네임으로 Member Entity에서 PK를 구한다음, PK를 가지고 작성한 글 목록을 찾아서 게시글 번호와 일치하는지 확인
	// 또는 Post Entity에 해당 닉네임을 만들어놓고 브라우저에서 닉네임과 게시글 번호 받으면 닉네임끼리 비교 후 삭제
}
