package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.dto.CommissionEstimateDetailsDto;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EstimateCreateResponseDto {

    @Schema(description = "의뢰 식별값")
    private Long commissionId;

    @Schema(description = "파트너 식별값")
    private Long partnerId;

    @Schema(description = "임시 가격")
    private int tmpPrice;

    @Schema(description = "확정 일자", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate;

    @Schema(description = "견적 코멘트")
    private String statement;

    @Schema(description = "회원 닉네임")
    private String nick;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "주거 형태")
    private HouseType houseType;

    @Schema(description = "청소 타입")
    private CleanType cleanType;

    @Schema(description = "희망 일자", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate;

    @Schema(description = "견적 상태")
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
