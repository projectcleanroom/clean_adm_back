package com.clean.cleanroom.estimate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EstimatePatchRequestDto {

    private Long commissionId;

    private int price;
}
