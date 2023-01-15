package com.greenUs.server.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRenewalRequest {

    @NotNull(message = "refreshToken 은 공백이면 안됩니다")
    private String refreshToken;
}
