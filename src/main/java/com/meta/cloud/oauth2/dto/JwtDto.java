package com.meta.cloud.oauth2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtDto {
    private final String accessToken;
    private final String tokenType = "Bearer";
}
