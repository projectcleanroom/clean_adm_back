package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;

@Getter
public class EstimateDeleteResponseDto {

    private String message;

    public EstimateDeleteResponseDto (Estimate estimate) {
        this.message = "삭제되었습니다.";
    }
}
