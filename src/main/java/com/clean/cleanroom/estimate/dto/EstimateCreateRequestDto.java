package com.clean.cleanroom.estimate.dto;

import lombok.Getter;

@Getter
public class EstimateCreateRequestDto {

    private Long commissionId;

    private int tmpPrice;

    private String statement;

    private String fixedDate;
}
