package com.clean.cleanroom.business.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BusinessInfoUpdateRequestDto {
    private Long partnerId;
    private Long businessNumber;
    private LocalDate openingDate;
    private String corporationName;
    private Long corporationNumber;
    private String location;

}