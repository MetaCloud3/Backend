package com.meta.cloud.service;

import com.meta.cloud.domain.User;
import com.meta.cloud.dto.user.*;
import com.meta.cloud.exception.UserException;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.config.security.JwtProvider;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(loginRequestDto.getLoginPw(), user.getLoginPw())) {
            return new JwtDto("Bearer", jwtProvider.createJwt(user.getId()));
        }
        throw new UserException(ResponseCode.INVALID_CREDENTIALS);
    }

    @Transactional
    public UserResponseDto join(JoinRequestDto joinRequestDto) {
        if(userRepository.existsByLoginId(joinRequestDto.getLoginId())){
            throw new UserException(ResponseCode.USER_ID_ALREADY_EXIST);
        }
        User user = joinRequestDto.toEntity(passwordEncoder.encode(joinRequestDto.getLoginPw()));
        return new UserResponseDto().toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ResponseCode.USER_NOT_FOUND));
        return new UserResponseDto().toDto(user);
    }
}
