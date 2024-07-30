package com.meta.cloud.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {

    private final boolean success;
    private final T Response;
    private final ApiError apiError;

    public ApiResult(boolean success, T response, ApiError apiError) {
        this.success = success;
        this.Response = response;
        this.apiError = apiError;
    }

    public static <T> ApiResult<T> OK(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> ERROR(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }
}
