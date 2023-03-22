package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.SignUpRequest;
import com.greenUs.server.global.error.ErrorResponse;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPageCommunityResponse;
import com.greenUs.server.member.dto.response.MyPageContentResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "유저", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 나의 주문 조회", description = "마이페이지 나의 주문 조회")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "마이페이지 나의 주문 조회 성공", content = @Content(schema = @Schema(implementation = MyPagePurchaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<MyPagePurchaseResponse> getMyOrder(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPagePurchaseResponse response = memberService.getMyOrder(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    // 마이페이지 관심상품
    @GetMapping("/me/products")
    public ResponseEntity<MemberResponse> getMyProducts(@AuthenticationPrincipal LoginMember loginMember) {
        // MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "마이페이지 내가 작성한 게시글/댓글 조회", description = "마이페이지 내가 작성한 게시글/댓글 조회")
    @ApiResponses(value =  {
        @ApiResponse(responseCode = "200", description = "마이페이지 내가 작성한 게시글/댓글 조회 성공", content = @Content(schema = @Schema(implementation = MyPageCommunityResponse.class))),
        @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me/communities/{kind}")
    public ResponseEntity<MyPageCommunityResponse> getMyCommunity(
        @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
        @Parameter(description = "게시글/댓글 조회 선택 (1:게시글, 2:댓글)", in = ParameterIn.PATH) @PathVariable Integer kind,
        @Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPageCommunityResponse response = memberService.getMyCommunity(memberResponse, kind, page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 컨텐츠", description = "마이페이지 컨텐츠")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "마이페이지 컨텐츠 조회 성공", content = @Content(schema = @Schema(implementation = MyPageContentResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me/contents")
    public ResponseEntity<Page<MyPageContentResponse>> getMyContents(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        Page<MyPageContentResponse> response = memberService.getMyContents(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 수정", description = "내 정보 수정")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "내 정보 수정 성공", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/me")
    public ResponseEntity<MemberResponse> updateMyInfo(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody MemberRequest memberRequest
            ) {
        MemberResponse memberResponse = memberService.updateMyInfo(loginMember.getId(), memberRequest);

        return ResponseEntity.ok(memberResponse);
    }

    @Operation(summary = "회원가입", description = "신규 회원일 경우 nickname 지정 필요")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signUp(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        MemberResponse memberResponse = memberService.signUp(loginMember.getId(), signUpRequest);
        return ResponseEntity.ok(memberResponse);
    }
}
