package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerLogoutResponseDto {

    @Schema(description = "로그아웃 되었습니다.")
    private String messege;


    public PartnerLogoutResponseDto(String messege) {
        this.messege = "로그아웃 되었습니다.";
    }
}
