package com.clean.cleanroom.exception;

import com.clean.cleanroom.util.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

import static com.clean.cleanroom.exception.ErrorMsg.NOT_LOGGED_ID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    // CustomException error
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ResponseDto> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorMsg());
        return ResponseDto.toExceptionResponseEntity(e.getErrorMsg());
    }

    // 정규식 error
    @ExceptionHandler({BindException.class})
    public ResponseEntity<ResponseDto<?>> bindException(BindException e) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                e.getFieldError().getDefaultMessage());
    }

    // 토큰 없을 시 error
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ResponseDto<?>> missingRequestHeaderException(MissingRequestHeaderException e) {
        return ResponseDto.toAllExceptionResponseEntity(NOT_LOGGED_ID.getHttpStatus(), NOT_LOGGED_ID.getDetails());
    }

    // 500 error
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto<?>> handleAll(final Exception ex) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ResponseDto<?>> handleIOException(IOException ex) {
        return ResponseDto.toAllExceptionResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
