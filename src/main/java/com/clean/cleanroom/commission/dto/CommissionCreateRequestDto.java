package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionCreateRequestDto {

    private String image;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Long addressId;
    private LocalDateTime desiredDate;
    private String significant;

}
