package com.clean.cleanroom.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessValidateRequestDto {

    @JsonProperty("service_key")
    @Schema(description = "서비스 키")
    private String serviceKey;

    @JsonProperty("businesses")
    @Schema(description = "사업자 정보 목록")
    private List<BusinessRequest> businesses;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessRequest {

        @JsonProperty("b_no")
        @Schema(description = "사업자등록번호", example = "0000000000")
        private String businessNumber; //필수 항목

        @JsonProperty("start_dt")
        @Schema(description = "개업일자 (YYYYMMDD)", example = "20240905")
        private String startDate; // 필수 항목

        @JsonProperty("p_nm")
        @Schema(description = "대표자 성명")
        private String representativeName; // 필수 항목

        @JsonProperty("b_nm")
        @Schema(description = "회사 이름")
        private String companyName;

        @JsonProperty("b_sector")
        @Schema(description = "사업 부문")
        private String businessSector;

        @JsonProperty("b_type")
        @Schema(description = "업종")
        private String businessType;
    }
}