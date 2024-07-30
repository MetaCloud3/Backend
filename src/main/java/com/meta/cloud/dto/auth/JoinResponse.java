package com.meta.cloud.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class JoinResponse {

    private UUID loginId;

    public JoinResponse(UUID loginId) {
        this.loginId = loginId;
    }
}
