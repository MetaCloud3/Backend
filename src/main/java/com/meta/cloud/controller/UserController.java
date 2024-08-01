package com.meta.cloud.controller;

import com.meta.cloud.util.ApiResponse;
import com.meta.cloud.dto.auth.JoinRequest;
import com.meta.cloud.dto.auth.LoginRequest;
import com.meta.cloud.service.UserService;
import com.meta.cloud.util.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest loginRequest) {
        //전역 예외 처리로 리팩토링 예정
        try{
            return ApiResponse.success(userService.login(loginRequest), ResponseCode.USER_LOGIN_SUCCESS.getMessage());
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/join")
    public ApiResponse<?> join(@RequestBody JoinRequest joinRequest) {
        return ApiResponse.success(userService.join(joinRequest), ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }

    //Auth 테스트용
    @GetMapping("/info")
    public ApiResponse<?> info(Authentication authentication) {
        return ApiResponse.success(authentication.getName() + "의 정보", ResponseCode.USER_READ_SUCCESS.getMessage());
    }
}