package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.partner.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerSignupResponseDto {

    @Schema(description = "회원가입 성공 메세지", example = "회원가입에 성공하였습니다.")
    private String messege;


    public PartnerSignupResponseDto(Partner partner) {
        this.messege = "회원가입에 성공하였습니다.";
    }
}
