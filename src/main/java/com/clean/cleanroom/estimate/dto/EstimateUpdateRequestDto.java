package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateUpdateRequestDto {

    private Long commissionId;

    private Long partnerId;

    private int price;

    private String statement;

    private LocalDateTime fixedDate;


    public EstimateUpdateRequestDto(Estimate estimate) {
        this.commissionId = estimate.getCommissionId();
        this.partnerId = getPartnerId();
        this.price = estimate.getPrice();
        this.statement = estimate.getStatement();
        this.fixedDate = getFixedDate();
    }
}
