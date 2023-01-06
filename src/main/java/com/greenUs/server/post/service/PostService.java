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

	private static final int PAGE_POST_COUNT = 3; // 한 화면에 보일 컨텐츠 수

	// 게시글 목록 조회
	public Page<PostResponseDto> getPostLists(Integer kind, Integer page, String orderCriteria) {

		/* 게시판 종류(kind), 정렬 조건(orderCriteria)에 따라 게시판 내용물을 불러온 후 반환
		*  게시판 종류(kind) -> 1: 자유게시판, 2: 정보공유, 3: 중고거래
		*  정렬 조건(orderCriteria) -> createdAt: 최신순, viewCnt: 조회순, recommendCnt: 추천순 */

		/* 넘겨받은 orderCriteria 를 이용해 내림차순하여 PageRequest 객체 반환
		*  PageRequest는 Pageable 인터페이스를 구현한 구현체 */
		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		// 게시판 종류(kind)에 해당하는 post 페이지 객체 반환
		Page<Post> post = postRepository.findByKind(kind, pageRequest);

		// 람다식을 활용하여 builder 패턴을 쓰지않고 간단히 DTO로 변환
		Page<PostResponseDto> postResponseDto = post.map(PostResponseDto::new);

		return postResponseDto;

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