package com.clean.cleanroom.business.dto;

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
    private String serviceKey;

    @JsonProperty("businesses")
    private List<BusinessRequest> businesses;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessRequest {

        @JsonProperty("b_no")
        private String businessNumber; // 사업자 번호 (필수)

        @JsonProperty("start_dt")
        private String startDate; // 개업 일자 (필수)

        @JsonProperty("p_nm")
        private String representativeName; // 대표자 성명 (필수)
    }
}
