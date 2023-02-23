package com.greenUs.server.infrastructure.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {

    private String email;
    private String name;
    private String picture;
}
