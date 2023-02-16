package com.greenUs.server.hashtag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenUs.server.hashtag.dto.HashtagResponseDto;
import com.greenUs.server.hashtag.service.HashtagService;
import com.greenUs.server.post.dto.PostResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "해시태그", description = "해시태그 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtags")
public class HashtagController {

	private final HashtagService hashtagService;

	@Operation(summary = "해시태그 인기글 조회", description = "해시태그 인기글을 조회 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "해시태그 인기글 조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/popularity")
	public ResponseEntity<HashtagResponseDto> popularity() {

		HashtagResponseDto hashtagResponseDto = hashtagService.getPopularityKeyword();

		return new ResponseEntity<>(hashtagResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "해시태그 검색", description = "해시태그를 기반으로 게시글을 검색 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "해시태그 검색 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/{keyword}")
	public ResponseEntity<PostResponseDto> search(@Parameter(description = "해시태그 키워드", in = ParameterIn.PATH) @PathVariable String keyword) {

		PostResponseDto postResponseDto = hashtagService.getSearchPostList(keyword);

		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}
}
