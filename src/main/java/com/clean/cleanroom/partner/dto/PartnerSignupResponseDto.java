package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerSignupResponseDto {

    @Schema(description = "회원가입에 성공하였습니다.")
    private String messege;


    public PartnerSignupResponseDto(String messege) {
        this.messege = "회원가입에 성공하였습니다.";
    }
}
