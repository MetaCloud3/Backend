package com.meta.cloud.util.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiHeader {

    private int code;
    private String message;
}
