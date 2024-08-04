package com.meta.cloud.exception;

import com.meta.cloud.util.api.ResponseCode;

public class UserException extends BaseException {
    public UserException(ResponseCode code) {
        super(code);
    }
}
