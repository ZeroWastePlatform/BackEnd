package com.greenUs.server.post.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostDto;
import com.greenUs.server.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "커뮤니티", description = "커뮤니티 API")
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}


	@Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 메서드")
	@GetMapping("/lists/{kind}") // 게시글 목록 조회
	public List<PostDto> list(@PathVariable("kind") @Min(1) @Max(3) Integer kind) {

		List<PostDto> postDtoList = postService.getPostList(kind);
		return postDtoList;
	}

	@Operation(summary = "게시글 상세 내용 조회", description = "게시글 상세 내용 조회 메서드")
	@GetMapping("/{id}") // 게시글 목록 조회
	public List<PostDto> detail(@PathVariable("id") Integer id) {

		List<PostDto> postDtoList = postService.getPostDetail(id);
		return postDtoList;
	}

	// -> 201 created와 함께 내용반환(ID, KIND는 꼭 필요) -> 변경사항 있으면 추후 수정하기
	@Operation(summary = "게시글 작성", description = "게시글 작성 메서드")
	@PostMapping("/writing") // 게시글 작성
	public ResponseEntity<PostDto> write(@RequestBody PostDto postDto) {

		PostDto result = postService.setPostWriting(postDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
}