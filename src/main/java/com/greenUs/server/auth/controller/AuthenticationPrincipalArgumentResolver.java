package com.greenUs.server.auth.controller;

import com.greenUs.server.auth.application.AuthService;
import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.auth.exception.EmptyAuthorizationHeaderException;
import com.greenUs.server.auth.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER_TYPE = "Bearer ";
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            throw new EmptyAuthorizationHeaderException();
        }

        if (!header.startsWith(BEARER_TYPE)) {
            throw new InvalidTokenException("token 형식이 잘못 되었어요");
        }

        String accessToken = header.substring(BEARER_TYPE.length()).trim();
        Long memberId = authService.extractMemberId(accessToken);

        return new LoginMember(memberId);
    }
}
