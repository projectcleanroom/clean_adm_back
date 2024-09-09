package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class VerifcationCodeRequestDto {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "인증번호")
    private String code;
}