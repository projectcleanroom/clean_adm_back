package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.enums.PartnerType;
import com.clean.cleanroom.partner.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerGetProfileResponseDto {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "파트너 폰번호")
    private String phoneNumber;

    @Schema(description = "담당자 이름")
    private String managerName;

    @Schema(description = "업체명")
    private String companyName;

    @Schema(description = "업종")
    private String businessType;

    @Schema(description = "파트너 타입")
    private PartnerType partnerType;

    public PartnerGetProfileResponseDto(Partner partner) {
        this.email = partner.getEmail();
        this.phoneNumber = partner.getPhoneNumber();
        this.managerName = partner.getManagerName();
        this.companyName = partner.getCompanyName();
        this.businessType = partner.getBusinessType();
        this.partnerType = partner.getPartnerType();
    }
}
