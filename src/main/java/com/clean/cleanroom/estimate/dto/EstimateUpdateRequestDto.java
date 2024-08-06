package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateUpdateRequestDto {

    private Long id;

    private Long commissionId;

    private Long partnerId;

    private int price;

    private String statement;

    private LocalDateTime fixedDate;


    public EstimateUpdateRequestDto (Estimate estimate, Commission commission) {
        this.id = estimate.getId();
        this.commissionId = commission.getId();
        this.partnerId = getPartnerId();
        this.price = estimate.getPrice();
        this.statement = estimate.getStatement();
        this.fixedDate = getFixedDate();
    }
}
