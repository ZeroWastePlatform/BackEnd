package com.greenUs.server.auth.dto;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthMember {

    private final String email;
    private final String displayName;
    private final String profileImageUrl;
    private final String refreshToken;

    public Member toMember() {
        return new Member(email, displayName, SocialType.GOOGLE, refreshToken);
    }
}
