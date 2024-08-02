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
public class ResponseDto<T> {
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //@JsonInclude: 메시지가 null인 경우 출력을 안한다라는 뜻
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //@JsonInclude: data가 null인 경우 출력을 안한다라는 뜻
    private T data;


    //ErrorMsg를 사용하여 응답을 생성
    public static ResponseEntity<ResponseDto> toExceptionResponseEntity(ErrorMsg errorMsg) {
        return ResponseEntity
                .status(errorMsg.getHttpStatus())
                .body(ResponseDto.builder()
                        .statusCode(errorMsg.getHttpStatus().value())
                        .data(errorMsg.getDetails())
                        .build()
                );
    }

    //HTTP 상태 코드와 메시지를 사용하여 응답을 생성
    public static ResponseEntity<ResponseDto<?>> toAllExceptionResponseEntity(HttpStatus httpStatus, String errorMsg) {
        return ResponseEntity
                .status(httpStatus.value())
                .body(ResponseDto.builder()
                        .statusCode(httpStatus.value())
                        .message(errorMsg)
                        .build()
                );
    }
}
