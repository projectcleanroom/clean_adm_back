package com.clean.cleanroom.estimate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateUpdateRequestDto {

    private Long id;

    private Long commissionId;

    private int price;

    private String statement;

    private LocalDateTime fixedDate;
}
