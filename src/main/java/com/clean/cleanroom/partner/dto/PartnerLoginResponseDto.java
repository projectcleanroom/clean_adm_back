package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerLoginResponseDto {

    @Schema(description = "로그인에 성공하였습니다.")
    private String messege;


    public PartnerLoginResponseDto(String messege) {
        this.messege = "로그인에 성공하였습니다.";
    }
}
