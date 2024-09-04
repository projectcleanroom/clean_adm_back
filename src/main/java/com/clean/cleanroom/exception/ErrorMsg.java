package com.clean.cleanroom.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@AllArgsConstructor
public enum ErrorMsg {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    IMAGE_INVALID(HttpStatus.BAD_REQUEST, 1001, "이미지가 잘못되었습니다."),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, 1002, "비밀번호가 옳지 않습니다."),
    MISSING_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, 1004, "Authorization 헤더가 없거나 형식이 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 1005, "잘못된 비밀번호 입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, 1009, "유효하지 않은 인증 코드입니다."),
    BAD_JSON_REQUEST(HttpStatus.BAD_REQUEST, 1010, "JSON 포맷에 적합하지 않은 요청입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,1011, "잘못된 요청입니다."),



    /* 401 UNAUTHORIZED : 인증되지 않음 */
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, 2001, "인증되지 않은 사용자입니다."),
    NOT_LOGGED_ID(HttpStatus.UNAUTHORIZED, 2002, "로그인이 되어있지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 2003, "Access Token이 만료되었습니다. Refresh Token을 사용하여 재인증하세요."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 2004, "Refresh Token이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 2005, "유효하지 않은 토큰입니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, 2006, "이메일 인증이 완료되지 않았습니다."),
    EXPIRED_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, 2007, "이메일 인증 코드가 만료되었습니다."),
    VERIFICATION_CODE_ATTEMPTS_EXCEEDED(HttpStatus.UNAUTHORIZED, 2008, "인증 시도 횟수 초과입니다."),


    /* 403 FORBIDDEN : 권한 없음 */
    NOT_A_SELLER(HttpStatus.FORBIDDEN, 3001, "판매자가 아닙니다."),
    NOT_A_BUYER(HttpStatus.FORBIDDEN, 3002, "구매자가 아닙니다."),
    COMMISSION_NOT_FOUND_OR_UNAUTHORIZED(HttpStatus.FORBIDDEN, 3003, "청소의뢰가 존재하지 않거나 권한이 없습니다."),


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "사용자를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, 4002, "후기를 찾을 수 없습니다."),
    COMMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, 4003, "청소의뢰를 찾을 수 없습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, 4004, "주소를 찾을 수 없습니다."),
    ESTIMATE_NOT_FOUND(HttpStatus.NOT_FOUND, 4005, "견적 내역을 찾을 수 없습니다."),
    NO_ESTIMATES_FOUND(HttpStatus.NOT_FOUND, 4006, "견적 내역이 존재하지 않습니다."),
    PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, 4007, "청소 업체를 찾을 수 없습니다."),
    BUSINESS_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, 4008, "사업자 등록을 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, 4009, "계좌번호를 찾을 수 없습니다."),
    INVALID_ID(HttpStatus.NOT_FOUND, 4010, "존재하지 않는 아이디입니다."),
    REQUEST_DATA_MALFORMED(HttpStatus.NOT_FOUND, 4011, "필수 요청 파라미터가 누락되었습니다."),



    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USER(HttpStatus.CONFLICT, 5001, "이미 가입된 사용자입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, 5002, "중복된 이메일입니다."),
    DUPLICATE_NICK(HttpStatus.CONFLICT, 5003, "중복된 닉네임입니다."),
    DUPLICATE_PHONENUMBER(HttpStatus.CONFLICT, 5004, "중복된 전화번호입니다."),
    DUPLICATE_COMPANYNAME(HttpStatus.CONFLICT, 5005, "중복된 회사명입니다."),



    /* 413 PAYLOAD_TOO_LARGE : 요청 데이터가 너무 큼 */
    TOO_LARGE_REQUEST(HttpStatus.PAYLOAD_TOO_LARGE, 6000, "요청 사업자번호 또는 정보가 100개를 초과하였습니다."),



    /* 500 INTERNAL SERVER ERROR : 그 외 서버 에러 (컴파일 관련) */
    FAILED_TO_EXECUTE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, 6001, "파일 실행에 실패했습니다."),
    FAILED_TO_COMPILE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, 6002, "파일 컴파일에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 6003, "시스템 오류가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String details;
}
