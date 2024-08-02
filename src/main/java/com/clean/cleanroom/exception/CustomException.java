package com.clean.cleanroom.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMsg errorMsg;

    public CustomException(ErrorMsg errorMsg) {
        super(errorMsg.getDetails());
        this.errorMsg = errorMsg;
    }

}