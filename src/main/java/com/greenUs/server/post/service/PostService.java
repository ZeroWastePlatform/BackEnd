package com.greenUs.server.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostDto;
import com.greenUs.server.post.dto.PostSpecs;
import com.greenUs.server.post.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	// 게시글 목록 조회
	@Transactional
	public List<PostDto> getPostList(Integer kind) {

		List<Post> postList = postRepository.findAll(PostSpecs.withKind(kind));
		List<PostDto> postDtoList = new ArrayList<>();

		for(Post post : postList) {
			PostDto postDto = PostDto.builder()
				.id(post.getId())
				.kind(post.getKind())
				.title(post.getTitle())
				.content(post.getContent())
				.build();
			postDtoList.add(postDto);
		}
		return postDtoList;
	}

	// 게시글 내용 불러오기
	@Transactional
	public List<PostDto> getPostDetail(Integer id) {

		List<Post> postList = postRepository.findAll(PostSpecs.withId(id));
		List<PostDto> postDtoList = new ArrayList<>();

		for(Post post : postList) {
			PostDto postDto = PostDto.builder()
				.id(post.getId())
				.kind(post.getKind())
				.title(post.getTitle())
				.content(post.getContent())
				.build();
			postDtoList.add(postDto);
		}
		return postDtoList;
	}

	// 게시글 작성
	@Transactional
	public PostDto setPostWriting(PostDto postDto) {

		Post post = postRepository.save(postDto.toEntity());
		PostDto result = PostDto.builder()
			.id(post.getId())
			.kind(post.getKind())
			.title(post.getTitle())
			.content(post.getContent())
			.build();

		return result;
	}

}