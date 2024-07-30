package com.meta.cloud.dto.auth;

import lombok.Data;

@Data
public class JoinRequest {

    private String loginId;
    private String loginPw;
    private String name;
    private String email;
}
