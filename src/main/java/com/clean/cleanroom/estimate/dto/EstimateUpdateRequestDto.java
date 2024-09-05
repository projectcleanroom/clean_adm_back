package com.clean.cleanroom.estimate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateUpdateRequestDto {

    @Schema(description = "견적 식별값")
    private long id;

    @Schema(description = "의뢰 식별값")
    private Long commissionId;

    @Schema(description = "확정 가격")
    private int price;

    @Schema(description = "견적 코멘트")
    private String statement;

    @Schema(description = "확정 일자", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate;
}
