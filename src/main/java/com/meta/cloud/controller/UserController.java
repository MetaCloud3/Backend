package com.meta.cloud.controller;

import com.meta.cloud.dto.ApiResult;
import com.meta.cloud.dto.auth.JoinRequest;
import com.meta.cloud.dto.auth.LoginRequest;
import com.meta.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody LoginRequest loginRequest) {
        //전역 예외 처리로 리팩토링 예정
        try{
            return ApiResult.OK(userService.login(loginRequest));
        } catch (Exception e){
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/join")
    public ApiResult<?> join(@RequestBody JoinRequest joinRequest) {
        return ApiResult.OK(userService.join(joinRequest));
    }

    //Auth 테스트용
    @GetMapping("/info")
    public ApiResult<?> info(Authentication authentication) {
        return ApiResult.OK(authentication.getName() + "의 정보");

    }
}
