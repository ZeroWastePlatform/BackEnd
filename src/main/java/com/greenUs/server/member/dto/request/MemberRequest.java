package com.greenUs.server.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class MemberRequest {

    @Min(1) @Max(10)
    @NotNull(message = "nickname 은 Null 이면 안됩니다")
    private String nickname;

    private String address;

    @NotNull(message = "phoneNum 은 Null 이면 안됩니다")
    private String phoneNum;

    private String interestArea;
}
