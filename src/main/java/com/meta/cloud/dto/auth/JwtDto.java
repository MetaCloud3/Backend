package com.meta.cloud.dto.auth;

import lombok.Data;

@Data
public class JwtDto {

    private String tokenType;
    private String accessToken;

    public JwtDto(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }
}
