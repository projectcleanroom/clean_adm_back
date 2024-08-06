package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.ErrorMsg;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//예외 발생 시 클라이언트에게 반환할 응답 형식 정의
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int statusCode;
    private int code;

    public static ResponseEntity<ResponseDto> toExceptionResponseEntity(HttpStatus httpStatus, int code) {
        return ResponseEntity
                .status(httpStatus)
                .body(ResponseDto.builder()
                        .code(code)
                        .build());
    }
}