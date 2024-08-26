package com.clean.cleanroom.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessValidateResponseDto {

    @JsonProperty("status_code")
    private String statusCode; // 응답 상태 코드

    @JsonProperty("request_cnt")
    private int requestCount; // 요청 정보 갯수

    @JsonProperty("valid_cnt")
    private int validCount; // 사업자 정보 갯수

    @JsonProperty("b_no")
    private String validMessage; // 유효성 검사 메세지

    @JsonProperty("data")
    private List<BusinessResponse> data; // 유효성 검사 리스트

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessResponse {

        @JsonProperty("b_no")
        private String businessNumber; // 사업자 등록 번호

        @JsonProperty("valid")
        private String valid; // 유효성 검사 결과 코드 ("01" - 정상, 기타 - 오류)

        @JsonProperty("valid_msg")
        private String validMessage; // 결과 메세지

        @JsonProperty("request_param")
        private RequestParam requestParam; // 사업자 정보 상세 메세지

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RequestParam {

            @JsonProperty("b_no")
            private String businessNumber; // 사업자 등록 번호

            @JsonProperty("start_dt")
            private String startDate; // 개업일자

            @JsonProperty("p_nm")
            private String representativeName; // 대표자 이름

            @JsonProperty("p_nm2")
            private String additionalRepresentativeName; // 대표자2 이름

            @JsonProperty("b_nm")
            private String companyName; // 회사 이름

            @JsonProperty("corp_no")
            private String corporationNumber; //법인 등록 번호

            @JsonProperty("b_sector")
            private String businessSector; // 사업 부문

            @JsonProperty("b_type")
            private String businessType; // 업종 추가

            @JsonProperty("b_adr")
            private String businessAddress; // 사업장 주소
        }
    }
}