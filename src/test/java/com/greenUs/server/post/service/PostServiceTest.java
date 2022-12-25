package com.greenUs.server.post.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostDto;
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



		// postService.getPostList(2);
		// System.out.println("postService = " + postService);
	}
}