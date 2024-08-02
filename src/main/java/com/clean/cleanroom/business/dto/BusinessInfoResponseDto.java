package com.clean.cleanroom.business.dto;

import com.clean.cleanroom.business.entity.BusinessInfo;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BusinessInfoResponseDto {
    private Long memberId;
    private Long partnerId;
    private Long businessNumber;
    private LocalDate openingDate;
    private String corporationName;
    private Long corporationNumber;
    private String location;


    public BusinessInfoResponseDto(BusinessInfo businessInfo) {
        this.memberId = businessInfo.getMembers().getId();
        this.partnerId = businessInfo.getPartner().getId();
        this.businessNumber = businessInfo.getBusinessNumber();
        this.openingDate = businessInfo.getOpeningDate();
        this.corporationName = businessInfo.getCorporationName();
        this.corporationNumber = businessInfo.getCorporationNumber();
        this.location = businessInfo.getLocation();
    }
}
