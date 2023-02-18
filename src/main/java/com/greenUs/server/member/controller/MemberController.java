package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    // 마이페이지 (기본: 나의 주문)
    @GetMapping("/me")
    public ResponseEntity<MyPagePurchaseResponse> findMe(@AuthenticationPrincipal LoginMember loginMember,
                                                         @RequestParam(required = false, defaultValue = "0", value = "page") Integer page) {
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
    @GetMapping("/me/communities")
    public ResponseEntity<MemberResponse> findMyCommunity(@AuthenticationPrincipal LoginMember loginMember) {
        // MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(null);
    }

    // 마이페이지 컨텐츠
    @GetMapping("/me/contents")
    public ResponseEntity<MemberResponse> findMyContents(@AuthenticationPrincipal LoginMember loginMember) {
        // MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(null);
    }

    // 내 정보 수정
    @PostMapping("/me")
    public ResponseEntity<MemberResponse> updateInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody MemberRequest memberRequest
            ) {
        MemberResponse memberResponse = memberService.updateInfo(loginMember.getId(), memberRequest);

        return ResponseEntity.ok(memberResponse);
    }
}
