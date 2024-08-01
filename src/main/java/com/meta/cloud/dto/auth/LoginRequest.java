package com.meta.cloud.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String loginId;
    private String loginPw;
}
