package com.greenUs.server.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

    @NotBlank(message = "인가 코드는 공백이면 안됩니다")
    private String code;

    @NotNull(message = "redirectUri 는 Null 이면 안됩니다.")
    private String redirectUri;
}
