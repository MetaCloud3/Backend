package com.meta.cloud.exception;

import com.meta.cloud.exception.auth.AlreadyExistLoginIdException;
import com.meta.cloud.exception.auth.InvalidCredentialsException;
import com.meta.cloud.exception.auth.InvalidJwtTokenException;
import com.meta.cloud.exception.auth.UserNotFoundException;
import com.meta.cloud.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    //회원가입시 아이디 중복 에러
    @ExceptionHandler(AlreadyExistLoginIdException.class)
    public ApiResponse<Void> alreadyExistLoginIdException(AlreadyExistLoginIdException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }

    //로그인시 ID에 해당하는 유저가 없을 때
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<Void> userNotFoundException(UserNotFoundException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }

    //로그인시 ID에 해당하는 비밀번호가 틀렸을 때
    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse<Void> invalidCredentialsException(InvalidCredentialsException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }

    //유효하지 않는 JWT 토큰을 전송했을 때
    @ExceptionHandler(InvalidJwtTokenException.class)
    public ApiResponse<Void> invalidJwtTokenException(InvalidJwtTokenException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }

    //dto @Validated 핸들링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), null);
    }
}
