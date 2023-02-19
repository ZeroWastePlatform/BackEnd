package com.greenUs.server.post.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.member.service.MemberService;
import com.greenUs.server.post.dto.PostPopularityResponseDto;
import com.greenUs.server.post.dto.PostRecommendationResponseDto;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "커뮤니티", description = "커뮤니티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final MemberRepository memberRepository;

	@Operation(summary = "게시글 목록 조회", description = "게시글 구분(kind)값과 현재 페이지(page), 정렬 조건(orderby), 검색 조건(searchtype), 검색어(searchby)를 파라미터로 받아 목록을 불러올 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/lists/{kind}")
	public ResponseEntity<Page<PostResponseDto>> list(
		@Parameter(description = "게시글 구분 값", in = ParameterIn.PATH) @PathVariable @Min(1) @Max(3) Integer kind,
		@Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
		@Parameter(description = "정렬 조건(createdAt: 최신순, viewCnt: 조회순, recommendCnt: 추천순)", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "createdAt", value = "orderby") String orderCriteria
	) {

		Page<PostResponseDto> postResponseDto = postService.getPostLists(kind, page, orderCriteria);
		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "게시글 검색 조회", description = "게시글 검색 키워드를 파라미터로 받아 검색 목록을 불러올 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 검색 조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping
	public ResponseEntity<Page<PostResponseDto>> search(
		@Parameter(description = "게시글 구분 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "search") String word,
		@Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page
	) {

		Page<PostResponseDto> postResponseDto = postService.getSearchLists(word, page);
		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "게시글 상세 내용 조회", description = "게시글 번호(id)를 파라미터로 받아 게시글을 상세 조회 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 상세 내용 조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDto> detail(@Parameter(description = "게시글 번호", in = ParameterIn.PATH) @PathVariable Long id) {

		postService.updateViewCnt(id);
		PostResponseDto postResponseDto = postService.getPostDetail(id);
		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	// -> 201 created와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 작성", description = "게시글 구분(kind)값과 제목(title), 내용(content), 가격(price) 등을 파라미터로 받아 게시글을 작성할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "게시글 작성 성공 - 게시글 목록으로 돌아가기 위해 게시글 구분(kind)값 반환", content = @Content(schema = @Schema(implementation = Integer.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping
	public ResponseEntity<Integer> write(
		@AuthenticationPrincipal LoginMember loginMember,
		@Parameter(description = "게시글 구분(kind), 제목(title), 내용(content), 가격(price)(중고 거래 게시글일 경우), 해시태그(hashtag)", in = ParameterIn.PATH) @RequestBody PostRequestDto postRequestDto) {

		Member member = memberRepository.findById(loginMember.getId()).orElseThrow(NotFoundMemberException::new);
		postRequestDto.setMember(member);

		Integer kind = postService.setPostWriting(postRequestDto);

		return new ResponseEntity<>(kind, HttpStatus.CREATED);
	}

	// -> 201 created와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 수정", description = "게시글 번호(id)와 게시글 구분(kind), 제목(title) 등을 파라미터로 받아 게시글을 수정할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "게시글 수정 성공 - 게시글 목록으로 돌아가기 위해 게시글 구분(kind)값 반환", content = @Content(schema = @Schema(implementation = Integer.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PutMapping("/{id}")
	public ResponseEntity<Integer> modify(
		@Parameter(description = "게시글 번호", in = ParameterIn.PATH) @PathVariable Long id,
		@Parameter(description = "게시글 구분(kind), 제목(title), 내용(content), 가격(price)(중고 거래 게시글일 경우), 해시태그(hashtag)", in = ParameterIn.PATH) @RequestBody PostRequestDto postRequestDto) {

		Integer kind = postService.setPostModification(id, postRequestDto);
		return new ResponseEntity<>(kind, HttpStatus.CREATED);
	}

	// -> 200 OK와 함께 게시글 목록 페이지로 넘어갈 수 있도록 그룹번호 반환
	@Operation(summary = "게시글 삭제", description = "게시글 번호(id)와 사용자 정보를 받아 게시글을 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 삭제 성공 - 게시글 목록으로 돌아가기 위해 게시글 구분(kind)값 반환", content = @Content(schema = @Schema(implementation = Integer.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@Parameter(description = "게시글 번호", in = ParameterIn.PATH) @PathVariable Long id) {

		Integer kind = postService.setPostDeletion(id);
		return new ResponseEntity<>(kind, HttpStatus.OK);
	}

	// -> 200 OK와 함께 추천 or 취소
	@Operation(summary = "게시글 추천", description = "게시글 번호(id)를 받아 게시글을 추천할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 추천 성공"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@PostMapping("/recommendations/{id}")
	public ResponseEntity recommend(@Parameter(description = "게시글 번호", in = ParameterIn.PATH) @PathVariable Long id) {

		postService.setPostRecommendation(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "오늘의 그리너스 인기글", description = "오늘의 인기 게시글 목록을 조회 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "오늘의 인기 게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostPopularityResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/popularity")
	public ResponseEntity<List<PostPopularityResponseDto>> popularity() {

		List<PostPopularityResponseDto> postPopularityResponseDto = postService.getPopularityPost();

		return new ResponseEntity<>(postPopularityResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "(자유게시판/중고거래/정보공유) 추천글", description = "추천글 목록을 조회 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "추천글 목록 조회 성공", content = @Content(schema = @Schema(implementation = PostPopularityResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	@GetMapping("/recommendations/{kind}")
	public ResponseEntity<List<PostRecommendationResponseDto>> recommendation(
		@Parameter(description = "게시글 구분 값", in = ParameterIn.PATH) @PathVariable @Min(1) @Max(3) Integer kind) {

		List<PostRecommendationResponseDto> postRecommendationResponseDto = postService.getRecommendationPost(kind);

		return new ResponseEntity<>(postRecommendationResponseDto, HttpStatus.OK);
	}
}