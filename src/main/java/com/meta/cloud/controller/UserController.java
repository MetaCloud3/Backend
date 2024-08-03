package com.meta.cloud.controller;

import com.meta.cloud.dto.user.JwtDto;
import com.meta.cloud.dto.user.UserResponseDto;
import com.meta.cloud.util.api.ApiResponse;
import com.meta.cloud.dto.user.JoinRequestDto;
import com.meta.cloud.dto.user.LoginRequestDto;
import com.meta.cloud.service.UserService;
import com.meta.cloud.util.api.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<JwtDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ApiResponse.success(userService.login(loginRequestDto), ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }

    @PostMapping("/join")
    public ApiResponse<UserResponseDto> join(@RequestBody @Valid JoinRequestDto joinRequestDto) {
        return ApiResponse.success(userService.join(joinRequestDto), ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> findById(@PathVariable("userId") String userId) {
        return ApiResponse.success(userService.findById(userId), ResponseCode.USER_READ_SUCCESS.getMessage());
    }

    //Auth 테스트용
    @GetMapping("/info")
    public ApiResponse<?> info(Authentication authentication) {
        return ApiResponse.success(authentication.getName() + "의 정보", ResponseCode.USER_READ_SUCCESS.getMessage());
    }
}
