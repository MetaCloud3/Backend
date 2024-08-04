package com.meta.cloud.dto.user;

import lombok.Getter;

@Getter
public class JwtDto {

    private String tokenType;
    private String accessToken;

    public JwtDto(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }
}
