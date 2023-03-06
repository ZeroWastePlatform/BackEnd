package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPageCommunityResponse;
import com.greenUs.server.member.dto.response.MyPageContentResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/members/me")
@RestController
public class MemberController {

    private final MemberService memberService;

    // 마이페이지 (기본: 나의 주문)
    @GetMapping
    public ResponseEntity<MyPagePurchaseResponse> getMyOrder(@AuthenticationPrincipal LoginMember loginMember,
                                                         @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPagePurchaseResponse response = memberService.getMyOrder(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    // 마이페이지 관심상품
    @GetMapping("/products")
    public ResponseEntity<MemberResponse> getMyProducts(@AuthenticationPrincipal LoginMember loginMember) {
        // MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(null);
    }

    // 마이페이지 커뮤니티
    @GetMapping("/communities/{kind}")
    public ResponseEntity<MyPageCommunityResponse> getMyCommunity(@AuthenticationPrincipal LoginMember loginMember,
                                                                   @PathVariable Integer kind,
                                                                   @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        MyPageCommunityResponse response = memberService.getMyCommunity(memberResponse, kind, page);
        return ResponseEntity.ok(response);
    }

    // 마이페이지 컨텐츠
    @GetMapping("/contents")
    public ResponseEntity<Page<MyPageContentResponse>> getMyContents(@AuthenticationPrincipal LoginMember loginMember,
                                                               @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
        MemberResponse memberResponse = memberService.findById(loginMember.getId());
        Page<MyPageContentResponse> response = memberService.getMyContents(memberResponse, page);
        return ResponseEntity.ok(response);
    }

    // 내 정보 수정
    @PostMapping
    public ResponseEntity<MemberResponse> updateMyInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody MemberRequest memberRequest
            ) {
        MemberResponse memberResponse = memberService.updateMyInfo(loginMember.getId(), memberRequest);

        return ResponseEntity.ok(memberResponse);
    }
}
