package com.clean.cleanroom.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final int code;

    public CustomException(ErrorMsg errorMsg) {
        this.httpStatus = errorMsg.getHttpStatus();
        this.code = errorMsg.getCode();
    }
}