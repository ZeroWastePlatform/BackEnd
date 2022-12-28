package com.greenUs.server.post.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
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
	public ResponseEntity<List<PostResponseDto>> list(@PathVariable @Min(1) @Max(3) Integer kind) {

		List<PostResponseDto> postResponseDto = postService.getPostLists(kind);
		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "게시글 상세 내용 조회", description = "게시글 상세 내용 조회 메서드")
	@GetMapping("/{id}") // 게시글 상세 내용 조회
	public ResponseEntity<PostResponseDto> detail(@PathVariable Long id) {

		PostResponseDto postResponseDto = postService.getPostDetail(id);
		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	// -> 201 created와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 작성", description = "게시글 작성 메서드")
	@PostMapping("/writing") // 게시글 작성
	public ResponseEntity<Integer> write(@RequestBody PostRequestDto postRequestDto) {

		Integer kind = postService.setPostWriting(postRequestDto);
		return new ResponseEntity<>(kind, HttpStatus.CREATED);
	}

	// -> 201 created와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 수정", description = "게시글 수정 메서드")
	@PutMapping("/{id}") // 게시글 수정
	public ResponseEntity<Integer> modify(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {

		Integer kind = postService.setPostModification(id, postRequestDto);
		return new ResponseEntity<>(kind, HttpStatus.CREATED);
	}

	// -> 200 OK와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 삭제", description = "게시글 삭제 메서드")
	@DeleteMapping("/{id}") // 게시글 삭제
	public ResponseEntity<Integer> delete(@PathVariable Long id) {

		Integer kind = postService.setPostdeletion(id);
		return new ResponseEntity<>(kind, HttpStatus.OK);
	}
}