package com.clean.cleanroom.business.dto;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "응답 상태 코드")
    private String statusCode;

    @JsonProperty("request_cnt")
    @Schema(description = "요청 정보 갯수")
    private int requestCount;

    @JsonProperty("valid_cnt")
    @Schema(description = "유효한 사업자 정보 갯수")
    private int validCount;

    @JsonProperty("b_no")
    @Schema(description = "유효성 검사 메세지")
    private String validMessage;

    @JsonProperty("data")
    @Schema(description = "유효성 검사 결과 리스트")
    private List<BusinessResponse> data;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessResponse {

        @JsonProperty("b_no")
        @Schema(description = "사업자 등록 번호", example = "1234567890")
        private String businessNumber;

        @JsonProperty("valid")
        @Schema(description = "유효성 검사 결과 코드", example = "01")
        private String valid; // 유효성 검사 결과 코드 ("01" - 정상, 기타 - 오류)

        @JsonProperty("valid_msg")
        @Schema(description = "유효성 검사 결과 메세지")
        private String validMessage;

        @JsonProperty("request_param")
        @Schema(description = "사업자 정보 상세 메세지")
        private RequestParam requestParam;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RequestParam {

            @JsonProperty("b_no")
            @Schema(description = "사업자 등록 번호", example = "1234567890")
            private String businessNumber;

            @JsonProperty("start_dt")
            @Schema(description = "개업일자 (YYYYMMDD)", example = "20240905")
            private String startDate;

            @JsonProperty("p_nm")
            @Schema(description = "대표자 이름", example = "김청소")
            private String representativeName;

            @JsonProperty("p_nm2")
            @Schema(description = "추가 대표자 이름", example = "김깨끗")
            private String additionalRepresentativeName;

            @JsonProperty("b_nm")
            @Schema(description = "회사 이름")
            private String companyName;

            @JsonProperty("corp_no")
            @Schema(description = "법인 등록 번호", example = "9876543210")
            private String corporationNumber;

            @JsonProperty("b_sector")
            @Schema(description = "사업 부문")
            private String businessSector;

            @JsonProperty("b_type")
            @Schema(description = "업종")
            private String businessType;

            @JsonProperty("b_adr")
            @Schema(description = "사업장 주소")
            private String businessAddress;
        }
    }
}