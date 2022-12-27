package com.greenUs.server.post.service;

import java.util.List;
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
	public List<PostResponseDto> getPostLists(Integer kind) {

		// 스트림 변환 후 리스트 객체로 반환
		return postRepository.findAllKindDesc(kind).stream()
			.map(PostResponseDto::new)
			.collect(Collectors.toList());
	}

	// 게시글 내용 불러오기
	@Transactional(readOnly = true)
	public PostResponseDto getPostDetail(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		return new PostResponseDto(post);
	}

	// 게시글 작성
	@Transactional
	public Integer setPostWriting(PostRequestDto postRequestDto) {

		// jpa save() 메서드 이용하면 insert()전 select()를 하게 되는데 게시글 작성은 ID가 auto increament 값이기 때문에 굳이 필요하나? 싶음
		// 지금과 같은 작은 프로젝트에서는 별로 필요없겠지만 불 필요한 쿼리를 생산할 필요는 없어보임.
		// return postRepository.save(postRequestDto.toEntity()).getKind();

		new Post().insert(
			postRequestDto.getKind(),
			postRequestDto.getTitle(),
			postRequestDto.getContent(),
			postRequestDto.getPrice());

		return postRequestDto.getKind();
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

		// save()에서 insert or update 에서 update로 판단되면 select() -> update() -> insert() 하는걸로 확인된다.
		// 굳이 insert() 왜하지? 찾아보기
		// postRepository.save(postRequestDto.toEntity());

		return postRequestDto.getKind();
	}

	public Integer setPostdeletion(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(()->new IllegalArgumentException("Post is not Existing"));
		// 여기도 findById랑 delete에서 select()가 겹친다.
		postRepository.delete(post);
		return post.getKind();
	}
}