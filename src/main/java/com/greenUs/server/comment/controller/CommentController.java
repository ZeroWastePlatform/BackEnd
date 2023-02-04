package com.greenUs.server.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.comment.dto.CommentRequestDto;
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
@RequestMapping("/posts")
public class CommentController {

	private final CommentService commentService;

	@Operation(summary = "댓글 작성", description = "게시글 번호(postId)와 내용(content)을 파라미터로 받아 댓글을 작성할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = Integer.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping("/comments")
	public ResponseEntity<Long> write(
		@Parameter(description = "게시글 번호(postId), 내용(content)", in = ParameterIn.PATH) @RequestBody CommentRequestDto commentRequestDto) {

		Long id = commentService.setCommentWriting(commentRequestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@Operation(summary = "댓글 수정", description = "댓글 번호(id)와 내용(content)을 파라미터로 받아 댓글을 수정할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = Integer.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PutMapping("/{id}/comments")
	public ResponseEntity<Long> modify(
		@Parameter(description = "댓글 번호(id)", in = ParameterIn.PATH) @PathVariable Long id,
		@Parameter(description = "내용(content)", in = ParameterIn.PATH) @RequestBody CommentRequestDto commentRequestDto) {

		Long commentId = commentService.setCommentModification(id, commentRequestDto);
		return new ResponseEntity<>(commentId, HttpStatus.CREATED);
	}
}
