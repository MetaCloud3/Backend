package com.meta.cloud.exception;

import com.meta.cloud.util.api.ResponseCode;

public class FileStoreException extends BaseException {
    public FileStoreException(ResponseCode code) {
        super(code);
    }
}
