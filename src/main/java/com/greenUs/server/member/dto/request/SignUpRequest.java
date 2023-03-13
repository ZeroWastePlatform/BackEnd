package com.greenUs.server.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "회원가입시 닉네임은 빈값이면 안됩니다.")
    String nickName;
}
