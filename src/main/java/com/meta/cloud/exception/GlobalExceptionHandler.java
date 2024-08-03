package com.meta.cloud.exception;

import com.meta.cloud.util.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 사용자 관련 Error
    @ExceptionHandler(UserException.class)
    public ApiResponse<Void> userExceptionHandler(UserException e) {
        return ApiResponse.fail(e.getCode(), null);
    }

    //dto @Validated 핸들링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), null);
    }
}
