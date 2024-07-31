package com.meta.cloud.oauth2.handler;

import com.meta.cloud.oauth2.dto.JwtDto;
import com.meta.cloud.oauth2.util.JwtUtil;
import com.meta.cloud.domain.entity.Users;
import com.meta.cloud.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Users user = (Users) authentication.getPrincipal();
        String token = jwtUtil.createJwt(user.getLoginId());
        JwtDto jwtDto = new JwtDto(token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(jwtDto));
    }
}
