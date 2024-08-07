package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.enums.PartnerType;
import com.clean.cleanroom.partner.entity.Partner;
import lombok.Getter;

@Getter
public class PartnerProfileResponseDto {

    private String email;
    private String phoneNumber;
    private String managerName;
    private String companyName;
    private String businessType;
    private PartnerType partnerType;

    public PartnerProfileResponseDto(Partner partner) {
        this.email = partner.getEmail();
        this.phoneNumber = partner.getPhoneNumber();
        this.managerName = partner.getManagerName();
        this.companyName = partner.getCompanyName();
        this.businessType = partner.getBusinessType();
        this.partnerType = partner.getPartnerType();
    }
}
