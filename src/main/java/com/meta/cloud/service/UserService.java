package com.meta.cloud.service;

import com.meta.cloud.domain.Users;
import com.meta.cloud.dto.auth.JoinRequest;
import com.meta.cloud.dto.auth.JoinResponse;
import com.meta.cloud.dto.auth.JwtDto;
import com.meta.cloud.dto.auth.LoginRequest;
import com.meta.cloud.exception.AlreadyExistLoginIdException;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expired}")
    private Long expiredMs;

    public JwtDto login(LoginRequest loginRequest) {

        Users user = userRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(loginRequest.getLoginPw(), user.getLoginPw())) {
            return new JwtDto("Bearer", JwtUtil.createJwt(loginRequest.getLoginId(), secretKey, expiredMs));
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    public JoinResponse join(JoinRequest joinRequest) {

        if(userRepository.existsByLoginId(joinRequest.getLoginId())){
            throw new AlreadyExistLoginIdException();
        }

        Users user = Users.builder()
                .loginId(joinRequest.getLoginId())
                .loginPw(passwordEncoder.encode(joinRequest.getLoginPw()))
                .name(joinRequest.getName())
                .email(joinRequest.getEmail())
                .totalStorage(0)
                .UsedStorage(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        return new JoinResponse(user.getUserId());
    }
}
