package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.partner.entity.Partner;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerLoginResponseDto {

    @Schema(description = "로그인에 성공하였습니다.")
    private String messege;


    public PartnerLoginResponseDto(Partner partner) {
        this.messege = "로그인에 성공하였습니다.";
    }
}
