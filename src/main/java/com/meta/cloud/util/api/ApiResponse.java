package com.meta.cloud.util.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final ApiHeader header;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private final String message;

    private static final int SUCCESS = 200;

    public ApiResponse(ApiHeader header, T data, String message) {
        this.header = header;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(new ApiHeader(HttpStatus.OK.value(), "SUCCESS"), data, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), "FAIL"), data, responseCode.getMessage());
    }

    public static <T> ApiResponse<T> fail(int code, String message, T data) {
        return new ApiResponse<T>(new ApiHeader(code, "FAIL"), data, message);
    }
}
