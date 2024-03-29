package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.SignUpRequest;
import com.greenUs.server.global.error.ErrorResponse;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.*;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.service.MemberService;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
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
import java.util.List;

@Tag(name = "유저", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 나의 프로필 조회", description = "마이페이지 나의 프로필 조회")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "마이페이지 나의 프로필 조회 성공", content = @Content(schema = @Schema(implementation = MyPageProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @GetMapping("/profile")
    public ResponseEntity<MyPageProfileResponse> getMyProfile(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember) {
        MyPageProfileResponse response = memberService.getMyProfile(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 나의 주문 조회", description = "마이페이지 나의 주문 조회")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "마이페이지 나의 주문 조회 성공", content = @Content(schema = @Schema(implementation = MyPagePurchaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<List<MyPagePurchaseResponse>> getMyPurchase(
            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember) {
        List<MyPagePurchaseResponse> response = memberService.getMyPurchase(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 관심 상품 조회", description = "마이페이지 관심 상품 조회")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "마이페이지 관심 상품 조회 성공", content = @Content(schema = @Schema(implementation = MyPageLikeProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @GetMapping("/me/products")
    public ResponseEntity<Page<MyPageLikeProductResponse>> getMyProducts(

            @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
            @Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page
    ) {
        Page<MyPageLikeProductResponse> response = memberService.findLikeProducts(loginMember.getId(), page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 내가 작성한 게시글/댓글 조회", description = "마이페이지 내가 작성한 게시글/댓글 조회")
    @ApiResponses(value =  {
        @ApiResponse(responseCode = "200", description = "마이페이지 내가 작성한 게시글/댓글 조회 성공", content = @Content(schema = @Schema(implementation = MyPageCommunityResponse.class))),
        @ApiResponse(responseCode = "400", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me/communities")
    public ResponseEntity<MyPageCommunityResponse> getMyCommunity(
        @Parameter(description = "accessToken 값", in = ParameterIn.HEADER) @AuthenticationPrincipal LoginMember loginMember,
        @Parameter(description = "내가 작성한 게시글 / 내가 작성한 댓글", in = ParameterIn.QUERY) @RequestParam(defaultValue = "내가 작성한 게시글") String kind,
        @Parameter(description = "현재 게시글 페이지 값", in = ParameterIn.PATH) @RequestParam(defaultValue = "0") Integer page) {
        MyPageCommunityResponse response = memberService.getMyCommunity(loginMember.getId(), kind, page);
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
        Page<MyPageContentResponse> response = memberService.getMyContents(loginMember.getId(), page);
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
