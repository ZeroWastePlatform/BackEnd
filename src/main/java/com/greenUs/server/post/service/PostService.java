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

	@Transactional
	public List<PostDto> getPostList(Integer kind) {

		List<Post> postList = postRepository.findAll(PostSpecs.withTitle(kind));
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
}