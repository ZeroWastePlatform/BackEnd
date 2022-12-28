package com.greenUs.server.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.repository.PostRepository;

@SpringBootTest
class PostServiceTest {

	@Autowired
	PostService postService;
	@Autowired
	PostRepository postRepository;

	@Test
	@Transactional
	@Rollback(value = false)
	void getPostList() {

		// for (int i = 1; i < 100; i++) {
		// 	PostDto postDto = new PostDto((long)i, 1, "제목"+1, "내용"+1);
		// 	Post post = postDto.toEntity();
		// 	Post saved = postRepository.save(post);
		// }
		// for (int i = 100; i < 200; i++) {
		// 	PostDto postDto = new PostDto((long)i, 2, "제목"+2, "내용"+2);
		// 	Post post = postDto.toEntity();
		// 	Post saved = postRepository.save(post);
		// }
		// for (int i = 200; i < 300; i++) {
		// 	PostDto postDto = new PostDto((long)i, 3, "제목"+3, "내용"+3);
		// 	Post post = postDto.toEntity();
		// 	Post saved = postRepository.save(post);
		// }

		postService.getPostLists(2);
		System.out.println("postService = " + postService);
	}

	// @Test
	// @Transactional
	// @Rollback(value = false)
	// void setPostWriting() {
	// 	PostRequestDto postDto = PostRequestDto.builder()
	// 		.kind(1)
	// 		.title("글 작성 제목")
	// 		.content("글 내용")
	// 		.build();
	//
	// 	postService.setPostWriting(postDto);
	// }
}