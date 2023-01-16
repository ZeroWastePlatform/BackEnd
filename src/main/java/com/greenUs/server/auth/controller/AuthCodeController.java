package com.greenUs.server.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCodeController {

    // 프론트 단에서의 authCode test
    @GetMapping("/login/oauth2/code/google")
    public String getAuthCode(
            String code
    ) {
        return code;
    }
}
