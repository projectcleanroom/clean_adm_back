package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateListResponseDto {

    private Long id;

    private Long commissionId;

    private int price;

    private LocalDateTime fixedDate;

    private String statement;

    public  EstimateListResponseDto(Estimate estimate) {
        this.id = estimate.getId();
        this.commissionId = estimate.getCommissionId().getId();
        this.fixedDate = estimate.getFixedDate();
        this.price = estimate.getPrice();
        this.statement = estimate.getStatement();
    }

}
