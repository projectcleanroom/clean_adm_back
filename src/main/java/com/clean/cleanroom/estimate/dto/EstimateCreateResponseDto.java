package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.dto.CommissionEstimateDetailsDto;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EstimateCreateResponseDto {

    private Long commissionId;
    private Long partnerId;
    private int tmpPrice;
    private LocalDateTime fixedDate;
    private String statement;
    private String nick;
    private String address;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate;
    private StatusType status;

    public EstimateCreateResponseDto(Estimate estimate, CommissionEstimateDetailsDto commissionDetails) {
        this.commissionId = estimate.getCommission().getId();
        this.partnerId = estimate.getPartner().getId();
        this.tmpPrice = estimate.getTmpPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.nick = commissionDetails.getNick();
        this.address = commissionDetails.getAddress();
        this.houseType = commissionDetails.getHouseType();
        this.cleanType = commissionDetails.getCleanType();
        this.desiredDate = commissionDetails.getDesiredDate();
        this.status = estimate.getStatus();
    }
}
