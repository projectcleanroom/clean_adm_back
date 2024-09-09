package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.partner.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerDeleteResponseDto {

    @Schema(description = "파트너 식별값")
    private Long id;

    @Schema(description = "탈퇴 메시지", example = "회원 탈퇴 완료.")
    private String messege;


    public PartnerDeleteResponseDto (Partner partner){
        this.id = partner.getId();
        this.messege = "회원 탈퇴 완료.";
    }

}
