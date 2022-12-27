package com.greenUs.server.post.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	// 게시글 목록 조회
	@Transactional(readOnly = true)
	public List<PostResponseDto> getPostList(Integer kind) {

		return postRepository.findAllKindDesc(kind).stream()
			.map(PostResponseDto::new)
			.collect(Collectors.toList());
	}

	// 게시글 내용 불러오기
	@Transactional(readOnly = true)
	public PostResponseDto getPostDetail(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));

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
			.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));

		post.update(
			postRequestDto.getKind(),
			postRequestDto.getTitle(),
			postRequestDto.getContent(),
			postRequestDto.getPrice());

		return postRequestDto.getKind();
	}
		// Optional<Post> result = postRepository.findById(id);
		//
		// result.ifPresent(t ->{
		// 	if (postRequestDto.getKind() != null) {
		// 		t.setKind(postRequestDto.getKind());
		// 	}
		// 	if (postRequestDto.getTitle() != null) {
		// 		t.setTitle(postRequestDto.getTitle());
		// 	}
		// 	if (postRequestDto.getContent() != null) {
		// 		t.setContent(postRequestDto.getContent());
		// 	}
		// });
		//
		// return result.get();

}