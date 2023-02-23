// package com.greenUs.server.post.service;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.greenUs.server.post.dto.PostRequestDto;
// import com.greenUs.server.post.repository.PostRepository;
//
// @SpringBootTest
// class PostServiceTest {
//
// 	@Autowired
// 	PostService postService;
// 	@Autowired
// 	PostRepository postRepository;
//
// 	@Test
// 	@Transactional
// 	@Rollback(value = false)
// 	void getPostList() {
// 	}
// }