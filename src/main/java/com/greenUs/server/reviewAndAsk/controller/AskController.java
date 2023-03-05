package com.greenUs.server.reviewAndAsk.controller;

import com.greenUs.server.auth.controller.AuthenticationPrincipal;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.reviewAndAsk.dto.AskDto;
import com.greenUs.server.reviewAndAsk.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AskController {
    private AskService askService;

    @PostMapping(value ="products/{productId}/asks")
    public Long addAsk(@PathVariable("productId") Long productId, @AuthenticationPrincipal LoginMember loginMember, @RequestBody AskDto askDto){
        return askService.addAsk(loginMember,productId,askDto);
    }
    @GetMapping(value ="products/{")
}
