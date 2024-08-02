package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EstimateResponseDto {

    private String message;

    public EstimateResponseDto(Estimate estimate) {
        this.message = "견적이 승인 되었습니다.";
    }
}

