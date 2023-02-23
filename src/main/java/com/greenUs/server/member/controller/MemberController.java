package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPageCommunityResponse;
import com.greenUs.server.member.dto.response.MyPageContentResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.exception.NotFoundMemberException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="회원", description = "회원 API")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 나의 주문",
            description = "처음 마이페이지에 들어올 때 보여지는 부분으로 회원 프로필에 대한 정보 + 주문 정보를 불러올 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 나의 주문 조회 성공", content = @Content(schema = @Schema(implementation = MyPagePurchaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저 엔티티를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<MyPagePurchaseResponse> findMe(@AuthenticationPrincipal LoginMember loginMember,
                                                         @Parameter(description = "나의 주문 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPagePurchaseResponse response = memberService.getMyPagePurchase(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    // 마이페이지 관심상품
    @GetMapping("/me/products")
    public ResponseEntity<MemberResponse> findMyProducts(@AuthenticationPrincipal LoginMember loginMember) {
        // MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(null);
    }

    // 마이페이지 커뮤니티
    @Operation(summary = "마이페이지 커뮤니티",
            description = "마이페이지 커뮤니티 부분으로 내가 쓴 글, 댓글을 불러올 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 커뮤니티 조회 성공", content = @Content(schema = @Schema(implementation = MyPageCommunityResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저 엔티티를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @GetMapping("/me/communities/{kind}")
    public ResponseEntity<MyPageCommunityResponse> findMyCommunity(@AuthenticationPrincipal LoginMember loginMember,
                                                                   @Parameter(description = "1: 글, 2: 댓글", in = ParameterIn.PATH) @PathVariable Integer kind,
                                                                   @Parameter(description = "나의 커뮤니티 페이지 값", in = ParameterIn.PATH) @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPageCommunityResponse response = memberService.getMyPageCommunity(memberResponse, kind, page);
        return ResponseEntity.ok(response);
    }

    // 마이페이지 컨텐츠
    @GetMapping("/me/contents")
    public ResponseEntity<Page<MyPageContentResponse>> findMyContents(@AuthenticationPrincipal LoginMember loginMember,
                                                               @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        Page<MyPageContentResponse> response = memberService.getMyPageContent(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 내 정보 수정",
            description = "마이페이지 내 정보 수정을 하는 페이지 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정을 완료했습니다.", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "유저 엔티티를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = NotFoundMemberException.class)))
    })
    @PostMapping("/me")
    public ResponseEntity<MemberResponse> updateInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody MemberRequest memberRequest
            ) {
        MemberResponse memberResponse = memberService.updateInfo(loginMember.getId(), memberRequest);

        return ResponseEntity.ok(memberResponse);
    }
}
