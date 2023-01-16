package com.greenUs.server.global.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;


@ConfigurationProperties("oauth.google")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class GoogleProperties {

    private final String clientId;
    private final String clientSecret;
    private final String oAuthEndPoint;
    private final String responseType;
    private final List<String> scopes;
    private final String tokenUri;
    private final String accessType; // 애플리케이션에서 액세스 토큰을 새로 고칠 수 있는지 여부
}
