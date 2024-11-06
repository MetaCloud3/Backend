package com.meta.cloud.exception;

import com.meta.cloud.util.api.ResponseCode;

public class FileDownloadException extends BaseException {
    public FileDownloadException(ResponseCode code) {
        super(code);
    }
}
