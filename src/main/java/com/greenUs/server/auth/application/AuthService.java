package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.response.AccessRefreshTokenResponse;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenCreator tokenCreator;

    @Transactional
    public AccessRefreshTokenResponse generateAccessAndRefreshToken(OAuthMember oAuthMember) {
        Member foundMember = findMember(oAuthMember);
        foundMember.change(oAuthMember.getRefreshToken());
        AuthToken authToken = tokenCreator.createAuthToken(foundMember.getId());
        return new AccessRefreshTokenResponse(authToken.getAccessToken(), authToken.getRefreshToken());
    }

    private Member findMember(OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.findByEmail(email);
        }
        // 회원가입이 필요한 상태!! 구현하기, -> 회원가입 해야 한다고 프론트 쪽에 알려줄까??
        return null;
    }

}
