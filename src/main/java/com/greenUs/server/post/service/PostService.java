package com.greenUs.server.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	private static final int PAGE_POST_COUNT = 10; // 한 화면에 보일 컨텐츠 수

	// 게시글 목록 조회
	public Page<Post> getPageLists(Integer kind, int page, String orderCriteria) {

		// 게시판 종류(kind), 정렬 조건(condition)에 따라 게시판 내용물을 불러온 후 스트림 변환 후 리스트 객체로 반환
		// 게시판 종류(kind) -> 1: 자유게시판, 2: 정보공유, 3: 중고거래
		// 정렬 조건(condition) -> 1: 최신순, 2: 조회순, 3: 추천순

		/* 넘겨받은 orderCriteria 를 이용해 내림차순하여 Pageable 객체 반환 */
		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		/* category_name에 해당하는 post 페이지 객체 반환 */
		Page<Post> postPageList = postRepository.findByKind(kind, pageRequest);

		return postPageList;

		// return postRepository.findKindConditionDesc(kind, condition).stream()
		// 	.map(PostResponseDto::new)
		// 	.collect(Collectors.toList());
	}

	// 게시글 내용 불러오기
	public PostResponseDto getPostDetail(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		return new PostResponseDto(post);
	}

	// 게시글 작성
	@Transactional
	public Integer setPostWriting(PostRequestDto postRequestDto) {

		return postRepository.save(postRequestDto.toEntity()).getKind();
	}

	// 게시글 수정
	@Transactional
	public Integer setPostModification(Long id, PostRequestDto postRequestDto) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		post.update(
			postRequestDto.getKind(),
			postRequestDto.getTitle(),
			postRequestDto.getContent(),
			postRequestDto.getPrice());

		return postRequestDto.getKind();
	}

	// 게시글 삭제
	@Transactional
	public Integer setPostdeletion(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		postRepository.delete(post);
		return post.getKind();
	}

	// 조회수 증가
	@Transactional
	public void updateViewCnt(Long id) {

		postRepository.updateViewCnt(id);
	}
}