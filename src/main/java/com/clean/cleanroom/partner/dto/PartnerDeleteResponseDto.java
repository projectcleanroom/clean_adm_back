package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.partner.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerDeleteResponseDto {

    private Long id;

    @Schema(description = "탈퇴 메시지", example = "회원 탈퇴 완료.")
    private String messege;


    public PartnerDeleteResponseDto (Partner partner){
        this.id = partner.getId();
        this.messege = "회원 탈퇴 완료.";
    }

}
