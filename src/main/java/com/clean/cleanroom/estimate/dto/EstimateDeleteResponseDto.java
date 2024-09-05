package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EstimateDeleteResponseDto {

    @Schema(description = "견적 식별값")
    private Long id;

    @Schema(description = "삭제 결과 메시지", example = "삭제되었습니다.")
    private String message;

    public EstimateDeleteResponseDto (Estimate estimate) {

        this.id = estimate.getId();
        this.message = "삭제되었습니다.";
    }
}
