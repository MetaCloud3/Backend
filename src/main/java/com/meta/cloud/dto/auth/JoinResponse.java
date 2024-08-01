package com.meta.cloud.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class JoinResponse {

    private UUID userId;

    public JoinResponse(UUID userId) {
        this.userId = userId;
    }
}
