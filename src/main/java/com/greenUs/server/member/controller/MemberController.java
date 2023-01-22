package com.greenUs.server.member.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findMe(@AuthenticationPrincipal LoginMember loginMember) {
        MemberResponse response = memberService.findById(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me")
    public ResponseEntity<MemberResponse> updateInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody MemberRequest memberRequest
            ) {
        MemberResponse memberResponse = memberService.updateInfo(loginMember.getId(), memberRequest);

        return ResponseEntity.ok(memberResponse);
    }
}
