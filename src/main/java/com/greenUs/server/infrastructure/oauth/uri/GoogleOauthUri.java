package com.greenUs.server.infrastructure.oauth.uri;

import com.greenUs.server.auth.application.OAuthUri;
import com.greenUs.server.global.config.properties.GoogleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOauthUri implements OAuthUri {

    private final GoogleProperties properties;

    @Override
    public String generate(String redirectUri) {
        return properties.getOAuthEndPoint() + "?"
                + "client_id=" + properties.getClientId() + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "response_type=code&"
                + "scope=" + String.join(" ", properties.getScopes()) + "&"
                + "access_type=" + properties.getAccessType() + "&"
                + "prompt=consent"; // prompt??
    }
}
