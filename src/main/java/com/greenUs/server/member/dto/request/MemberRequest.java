package com.greenUs.server.member.dto.request;

import com.greenUs.server.member.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class MemberRequest {

    @Min(1) @Max(10)
    @NotNull(message = "nickname 은 Null 이면 안됩니다")
    private String nickname;

    private Address address;

    @NotNull(message = "phoneNum 은 Null 이면 안됩니다")
    private String phoneNum;

    private String interestArea;
}
