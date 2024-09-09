package com.clean.cleanroom.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AccountRequestDto {

    @Schema(description = "계좌번호")
    private String accountNumber;

    @Schema(description = "은행")
    private String bank;
}
