package com.greenUs.server.auth.application;

@FunctionalInterface
public interface OAuthUri {

    String generate(final String redirectUri);
}
