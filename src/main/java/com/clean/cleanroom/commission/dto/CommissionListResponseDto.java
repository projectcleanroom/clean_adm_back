package com.clean.cleanroom.commission.dto;


import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionListResponseDto {

    private String memberNick;

    private String image;

    private int size;

    private HouseType houseType;

    private CleanType cleanType;

    private String address;

    private LocalDateTime desiredDate;

    private String significant;


    public CommissionListResponseDto(String memberNick,
                                     String image,
                                     int size,
                                     HouseType houseType,
                                     CleanType cleanType,
                                     String address,
                                     LocalDateTime desiredDate,
                                     String significant) {
        this.memberNick = memberNick;
        this.image = image;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.address = address;
        this.desiredDate = desiredDate;
        this.significant = significant;
    }

}

