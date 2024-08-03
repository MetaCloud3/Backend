package com.meta.cloud.dto.user;

import lombok.Getter;

@Getter
public class JoinResponseDto {

    private String userId;

    public JoinResponseDto(String userId) {
        this.userId = userId;
    }
}
