package com.clean.cleanroom.estimate.dto;

import lombok.Getter;

@Getter
public class EstimateCreateRequestDto {

    private Long commissionId;

    private Long partner;

    private int price;

    private String statement;

    private String fixedDate;
}
