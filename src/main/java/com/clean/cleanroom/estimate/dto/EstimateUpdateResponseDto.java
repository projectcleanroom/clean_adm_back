package com.clean.cleanroom.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EstimateUpdateResponseDto {

    private Long id;

    private Long commissionId;

    private Long partnerId;

    private int price;

    private String statement;

    private LocalDateTime fixedDate;
}
