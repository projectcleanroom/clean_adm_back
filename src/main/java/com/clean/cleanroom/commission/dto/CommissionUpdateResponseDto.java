package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommissionUpdateResponseDto {

    private Long commissionId;
    private String memberNick;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private String address;
    private LocalDateTime desiredDate;
    private String significant;



    public CommissionUpdateResponseDto(Commission commission) {
        this.commissionId = commission.getId();
        this.memberNick = commission.getMembers().getNick();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.address = commission.getAddress().getAddress();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
    }

}
