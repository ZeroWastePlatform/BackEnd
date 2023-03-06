package com.greenUs.server.comment.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.comment.dto.CommentRequestDto;
import com.greenUs.server.comment.dto.CommentResponseDto;
import com.greenUs.server.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "커뮤니티 댓글", description = "커뮤니티 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/comments")
public class CommentController {

	private final CommentService commentService;

	@Operation(summary = "댓글 조회", description = "게시글 번호(post-id)를 파라미터로 받아 게시글의 댓글을 조회 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 댓글 조회 성공", content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/{post-id}")
	public ResponseEntity<Page<CommentResponseDto>> getCommentLists(
		@Parameter(description = "게시글 번호", in = ParameterIn.PATH) @PathVariable(value = "post-id") Long postId,
		@Parameter(description = "현재 댓글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {

		Page<CommentResponseDto> commentResponseDto = commentService.getCommentLists(postId, page);
		return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "댓글 작성", description = "게시글 번호(postId)와 내용(content)을 파라미터로 받아 댓글을 작성할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping
	public ResponseEntity createComment(
		@Parameter(description = "게시글 번호(postId), 내용(content)", in = ParameterIn.PATH) @RequestBody CommentRequestDto commentRequestDto) {

		commentService.createComment(commentRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "대댓글 작성", description = "게시글 번호(post-id)와 댓글 번호(id), 내용(content)을 파라미터로 받아 대댓글을 작성할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping("/{parent-id}")
	public ResponseEntity createRecomment(
		@Parameter(description = "댓글 번호(parent-id)", in = ParameterIn.PATH) @PathVariable(value = "parent-id") Long parentId,
		@Parameter(description = "게시글 번호(postId), 내용(content)", in = ParameterIn.PATH) @RequestBody CommentRequestDto commentRequestDto) {

		commentService.createRecomment(parentId, commentRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "댓글 수정", description = "댓글 번호(id)와 내용(content)을 파라미터로 받아 댓글을 수정할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "댓글 수정 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PutMapping("/{id}")
	public ResponseEntity updateComment(
		@Parameter(description = "댓글 번호(id)", in = ParameterIn.PATH) @PathVariable Long id,
		@Parameter(description = "내용(content)", in = ParameterIn.PATH) @RequestBody CommentRequestDto commentRequestDto) {

		commentService.updateComment(id, commentRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "댓글 삭제", description = "댓글 번호(id)를 받아 댓글을 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@DeleteMapping("/{id}")
	public ResponseEntity deleteComment(@Parameter(description = "댓글 번호", in = ParameterIn.PATH) @PathVariable Long id) {

		commentService.deleteComment(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
