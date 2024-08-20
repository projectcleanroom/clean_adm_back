package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateConfirmListResponseDto {

    private long id;
    private Long commissionId;
    private Long partnerId;
    private int tmpPrice;
    private LocalDateTime fixedDate; // 확정일자
    private String statement;
    private String nick;
    private String address;
    private String image;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate; // 희망날짜
    private StatusType status;
    private StatusType commissionStatus;


    public EstimateConfirmListResponseDto (Estimate estimate,
                                   Members members,
                                   Address address,
                                   Commission commission) {
        this.id = estimate.getId();
        this.commissionId = estimate.getCommission().getId();
        this.partnerId = estimate.getPartner().getId();
        this.tmpPrice = estimate.getTmpPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.nick = members.getNick();
        this.address = address.getAddress();
        this.image = commission.getImage();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.desiredDate = commission.getDesiredDate();
        this.status = estimate.getStatus();
        this.commissionStatus = commission.getStatus();
    }
}
