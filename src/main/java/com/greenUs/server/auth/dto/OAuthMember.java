package com.greenUs.server.auth.dto;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthMember {

    private final String email;
    private final String displayName;
    private final String profileImageUrl;
    private final String refreshToken;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .name(displayName)
                .socialType(SocialType.GOOGLE)
                .build();
    }
}
