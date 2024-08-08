package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateListResponseDto {

    private Long commissionId;

    private Long partnerId;

    private int price;

    private LocalDateTime fixedDate;

    private String statement;

    private Boolean approved;

    public EstimateListResponseDto(Estimate estimate) {
        this.commissionId = estimate.getCommissionId().getId();
        this.partnerId = estimate.getPartner().getId();
        this.price = estimate.getPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.approved = estimate.getApproved();
    }

}
