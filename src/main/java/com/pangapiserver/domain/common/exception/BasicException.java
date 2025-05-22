package com.pangapiserver.domain.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicException extends RuntimeException {
    private final StatusCode statusCode;

    @Override
    public String getMessage() {
        return statusCode.getMessage();
    }
}
