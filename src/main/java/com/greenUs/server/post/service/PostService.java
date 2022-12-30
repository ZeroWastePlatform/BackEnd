package com.greenUs.server.post.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PrePersist;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	// 게시글 목록 조회
	public List<PostResponseDto> getPostLists(Integer kind) {

		// 스트림 변환 후 리스트 객체로 반환
		return postRepository.findAllKindDesc(kind).stream()
			.map(PostResponseDto::new)
			.collect(Collectors.toList());
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
}