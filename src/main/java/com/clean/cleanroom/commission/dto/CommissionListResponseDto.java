package com.clean.cleanroom.commission.dto;


import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionListResponseDto {

    @Schema(description = "의뢰 식별값")
    private long id;

    @Schema(description = "회원 닉네임")
    private String memberNick;

    @Schema(description = "이미지")
    private String image;

    @Schema(description = "평수")
    private int size;

    @Schema(description = "주거 형태")
    private HouseType houseType;

    @Schema(description = "청소 타입")
    private CleanType cleanType;

    @Schema(description = "의뢰 상태")
    private StatusType status;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "희망 날짜", example = "2024-09-09T10:00:00")
    private LocalDateTime desiredDate;

    @Schema(description = "특이사항")
    private String significant;


    public CommissionListResponseDto(Long id,
                                     String memberNick,
                                     String image,
                                     int size,
                                     HouseType houseType,
                                     CleanType cleanType,
                                     StatusType status,
                                     String address,
                                     LocalDateTime desiredDate,
                                     String significant) {
        this.id = id;
        this.memberNick = memberNick;
        this.image = image;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.status = status;
        this.address = address;
        this.desiredDate = desiredDate;
        this.significant = significant;
    }

}

