package com.clean.cleanroom.estimate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateCreateResponseDto {

    private Long commissionId;
    private Long partnerId;
    private int price;
    private LocalDateTime fixedDate;
    private String statement;

    public EstimateCreateResponseDto (Long commissionId, Long partnerId, int price, LocalDateTime fixedDate, String statement) {
        this.commissionId = commissionId;
        this.partnerId = partnerId;
        this.price = price;
        this.fixedDate = fixedDate;
        this.statement = statement;
    }
}
