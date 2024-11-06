package com.meta.cloud.exception;

import com.meta.cloud.util.api.ResponseCode;

public class ShareException extends BaseException {
    public ShareException(ResponseCode code) {
        super(code);
    }
}
