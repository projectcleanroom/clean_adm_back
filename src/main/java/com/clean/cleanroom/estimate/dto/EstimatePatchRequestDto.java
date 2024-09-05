package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EstimatePatchRequestDto {

    @Schema(description = "의뢰 식별값")
    private Long commissionId;

    @Schema(description = "임시 가격")
    private int tmpPrice;

    @Schema(description = "견적 상태")
    private StatusType status;
}
