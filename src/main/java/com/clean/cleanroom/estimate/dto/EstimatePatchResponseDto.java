package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EstimatePatchResponseDto {

    private Long id;
    private Long commissionId;
    private Long partnerId;
    private int Price;
    private LocalDateTime fixedDate; // 확정일자
    private String statement;
    private String nick;
    private String address;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate; // 희망날짜

    public EstimatePatchResponseDto (Estimate estimate,
                                      Members members,
                                      Address address,
                                      Commission commission) {
        this.commissionId = estimate.getCommission().getId();
        this.partnerId = estimate.getPartner().getId();
        this.Price = estimate.getPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.nick = members.getNick();
        this.address = address.getAddress();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.desiredDate = commission.getDesiredDate();
    }
}
