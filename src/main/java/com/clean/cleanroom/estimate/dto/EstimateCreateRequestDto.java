package com.clean.cleanroom.estimate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EstimateCreateRequestDto {

    @Schema(description = "의뢰 식별값")
    private Long commissionId;

    @Schema(description = "임시 가격")
    private int tmpPrice;

    @Schema(description = "견적 코멘트")
    private String statement;

    @Schema(description = "확정 일자", example = "2024-09-05T10:00:00")
    private String fixedDate;
}
