package com.greenUs.server.auth.application;

import com.greenUs.server.auth.domain.AuthToken;
import com.greenUs.server.auth.dto.OAuthMember;
import com.greenUs.server.auth.dto.request.TokenRenewalRequest;
import com.greenUs.server.auth.dto.response.AccessRefreshTokenResponse;
import com.greenUs.server.auth.dto.response.AccessTokenResponse;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
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
        if (foundMember == null)
            return null;
        foundMember.change(oAuthMember.getRefreshToken());
        AuthToken authToken = tokenCreator.createAuthToken(foundMember.getId());
        return new AccessRefreshTokenResponse(authToken.getAccessToken(), authToken.getRefreshToken());
    }

    private Member findMember(OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email))
            return memberRepository.findByEmail(email);
        return null;
    }

    public AccessTokenResponse generateAccessToken(TokenRenewalRequest tokenRenewalRequest) {
        String refreshToken = tokenRenewalRequest.getRefreshToken();
        AuthToken authToken = tokenCreator.renewAuthToken(refreshToken);
        return new AccessTokenResponse(authToken.getAccessToken());
    }

    public Long extractMemberId(String accessToken) {
        Long memberId = tokenCreator.extractPayload(accessToken);
        memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        return memberId;
    }
}
