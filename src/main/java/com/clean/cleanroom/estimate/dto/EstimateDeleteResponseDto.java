package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;

@Getter
public class EstimateDeleteResponseDto {

    private Long id;
    private String message;

    public EstimateDeleteResponseDto (Estimate estimate) {

        this.id = estimate.getId();
        this.message = "삭제되었습니다.";
    }
}
