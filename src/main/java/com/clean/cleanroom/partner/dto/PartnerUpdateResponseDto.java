package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.enums.PartnerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerUpdateResponseDto {

    private String eamil;

    private String phoneNumber;

    private String managerName;

    private String companyName;

    private String businessType;

    private PartnerType partnerType;

    @Schema(description = "로그인에 성공하였습니다.")
    private String messege;

    
}
