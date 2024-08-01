package com.meta.cloud.exception;

import com.meta.cloud.exception.auth.AlreadyExistLoginIdException;
import com.meta.cloud.util.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(AlreadyExistLoginIdException.class)
    public ApiResponse<Void> alreadyExistLoginIdException(AlreadyExistLoginIdException e) {
        return ApiResponse.fail(e.getResponseCode(), null);
    }
}
