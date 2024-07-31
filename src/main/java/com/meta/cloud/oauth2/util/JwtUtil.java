package com.meta.cloud.oauth2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private final SecretKey secretKey;
    private final Long expirationMs;

    public JwtUtil(String secret, Long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    // Jwt 생성
    public String createJwt(String loginId) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰을 분석하여 만료되었는지 확인
    public boolean isExpired(String token) {
        return getClaimsFromToken(token)
                .getExpiration()
                .before(new Date());
    }

    // 토큰에서 로그인 아이디 추출
    public String getLoginId(String token) {
        return getClaimsFromToken(token)
                .get("loginId", String.class);
    }

    // 토큰의 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 클레임 추출
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
