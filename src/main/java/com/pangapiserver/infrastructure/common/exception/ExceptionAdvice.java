package com.pangapiserver.infrastructure.common.exception;

import com.pangapiserver.domain.common.exception.BasicException;
import com.pangapiserver.domain.common.exception.StatusCode;
import com.pangapiserver.infrastructure.common.dto.BaseResponse;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    @ExceptionHandler(BasicException.class)
    private ResponseEntity<BaseResponse> handleBasicException(BasicException e) {
        StatusCode statusCode = (StatusCode) e;
        BaseResponse<?> response = BaseResponse.error(
                statusCode.getHttpStatus(),
                statusCode.getMessage()
        );
        return new ResponseEntity<>(response, statusCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<BaseResponse> handleAll(Exception e) {
        BaseResponse response = BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
