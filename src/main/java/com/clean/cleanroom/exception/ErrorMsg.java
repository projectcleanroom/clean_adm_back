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
    IMAGE_INVALID(BAD_REQUEST,"이미지가 잘못 되었습니다."),
    PASSWORD_INCORRECT(BAD_REQUEST,"비밀번호가 옳지 않습니다."),
    INVALID_TOKEN(BAD_REQUEST, "유효하지 않은 토큰입니다."),
    MISSING_AUTHORIZATION_HEADER(BAD_REQUEST, "Authorization 헤더가 없거나 형식이 올바르지 않습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "인증된 사용자가 아닙니다."),
    NOT_LOGGED_ID(UNAUTHORIZED, "로그인이 되어있지 않습니다."),
    EXPIRED_ACCESS_TOKEN(UNAUTHORIZED, "Access Token이 만료되었습니다. Refresh Token을 사용하여 재인증하세요."),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "Refresh Token이 만료되었습니다. 다시 로그인해주세요."),


    /* 403 FORBIDDEN : 권한 없음 */
    NOT_A_SELLER(FORBIDDEN, "판매자가 아닙니다"),
    NOT_A_BUYER(FORBIDDEN, "구매자가 아닙니다"),
    COMMISSION_NOT_FOUND_OR_UNAUTHORIZED(FORBIDDEN, "청소의뢰가 존재하지 않거나 권한이 없습니다."),



    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(NOT_FOUND, "후기를 찾을 수 없습니다."),
    COMMISSION_NOT_FOUND(NOT_FOUND, "청소의뢰를 찾을 수 없습니다."),
    ADDRESS_NOT_FOUND(NOT_FOUND, "주소를 찾을 수 없습니다."),
    ESTIMATE_NOT_FOUND(NOT_FOUND, "견적 내역을 찾을 수 없습니다."),
    NO_ESTIMATES_FOUND(NOT_FOUND, "견적 내역이 존재하지 않습니다."),
    PARTNER_NOT_FOUND(NOT_FOUND, "청소 업체를 찾을 수 없습니다."),
    BUSINESS_INFO_NOT_FOUND(NOT_FOUND, "사업자 등록을 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "계좌번호를 찾을 수 없습니다."),



    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USER(CONFLICT,"이미 가입된 사용자입니다."),
    DUPLICATE_EMAIL(CONFLICT,"중복된 이메일입니다."),


    /* 500 INTERNAL SERVER ERROR : 그 외 서버 에러 (컴파일 관련) */
    FAILED_TO_EXECUTE_FILE(INTERNAL_SERVER_ERROR, "파일 실행에 실패했습니다."),
    FAILED_TO_COMPILE_FILE(INTERNAL_SERVER_ERROR, "파일 컴파일에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String details;
}
