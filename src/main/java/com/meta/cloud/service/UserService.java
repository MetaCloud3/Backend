package com.meta.cloud.service;

import com.meta.cloud.domain.Users;
import com.meta.cloud.dto.auth.JoinRequest;
import com.meta.cloud.dto.auth.JoinResponse;
import com.meta.cloud.dto.auth.JwtDto;
import com.meta.cloud.dto.auth.LoginRequest;
import com.meta.cloud.exception.auth.AlreadyExistLoginIdException;
import com.meta.cloud.exception.auth.InvalidCredentialsException;
import com.meta.cloud.exception.auth.UserNotFoundException;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.config.security.JwtProvider;
import com.meta.cloud.util.ResponseCode;
import lombok.RequiredArgsConstructor;
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
    private final JwtProvider jwtProvider;

    public JwtDto login(LoginRequest loginRequest) {

        Users user = userRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new UserNotFoundException(ResponseCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(loginRequest.getLoginPw(), user.getLoginPw())) {
            return new JwtDto("Bearer", jwtProvider.createJwt(loginRequest.getLoginId()));
        }

        throw new InvalidCredentialsException(ResponseCode.INVALID_CREDENTIALS);
    }

    public JoinResponse join(JoinRequest joinRequest) {

        if(userRepository.existsByLoginId(joinRequest.getLoginId())){
            throw new AlreadyExistLoginIdException(ResponseCode.USER_ID_ALREADY_EXIST);
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
