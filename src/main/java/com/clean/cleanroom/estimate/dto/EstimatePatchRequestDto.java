package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.enums.StatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EstimatePatchRequestDto {

    private int tmpPrice;

    private Long commissionId;

    private StatusType status;
}
