package com.meta.cloud.exception;

import com.meta.cloud.exception.auth.AlreadyExistLoginIdException;
import com.meta.cloud.exception.auth.UserNotFoundException;
import com.meta.cloud.util.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    //회원가입시 아이디 중복 에러
    @ExceptionHandler(AlreadyExistLoginIdException.class)
    public ApiResponse<Void> alreadyExistLoginIdException(AlreadyExistLoginIdException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }

    //로그인시 유저가 없을 때
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<Void> userNotFoundException(UserNotFoundException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }
}
