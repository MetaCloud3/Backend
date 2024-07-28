package com.meta.cloud.service;

import com.meta.cloud.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    public String login(String username, String password) {
        // 인증 과정
        return JwtUtil.createJwt(username, secretKey, expiredMs);
    }
}
