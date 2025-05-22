package com.pangapiserver.infrastructure.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static <T> BaseResponse<T> ok(HttpStatus status, T data) {
        return BaseResponse.<T>builder()
            .status(status)
            .data(data)
            .build();
    }

    public static <T> BaseResponse<T> error(HttpStatus status, String message) {
        return BaseResponse.<T>builder()
            .status(status)
            .message(message)
            .build();
    }
}