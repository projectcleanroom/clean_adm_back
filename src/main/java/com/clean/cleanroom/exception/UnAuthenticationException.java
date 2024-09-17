package com.clean.cleanroom.exception;

import org.springframework.http.HttpStatus;

// 예외가 발생할 때 자동으로 401 Unauthorized 상태 반환
public class UnAuthenticationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final int code;
    private final String details;  // 추가된 필드

    // ErrorMsg를 반환하는 메서드 추가
    private final ErrorMsg errorMsg;  // ErrorMsg 필드 추가

    public UnAuthenticationException(ErrorMsg errorMsg) {
        super(errorMsg.getDetails());
        this.httpStatus = errorMsg.getHttpStatus();
        this.code = errorMsg.getCode();
        this.details = errorMsg.getDetails();  // 추가된 필드 초기화
        this.errorMsg = errorMsg;
    }
}
