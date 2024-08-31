package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommissionEstimateDetailsDto {
    // 견적 생성 메서드에서 공간복잡도 줄이기 위해 생성된 Dto


    private final Long commissionId;
    private final HouseType houseType;
    private final CleanType cleanType;
    private final LocalDateTime desiredDate;
    private final String nick;
    private final String address;
}
