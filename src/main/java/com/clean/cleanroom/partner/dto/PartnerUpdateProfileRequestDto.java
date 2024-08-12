package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.enums.PartnerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PartnerUpdateProfileRequestDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@$%^&*_])[a-zA-Z\\d~!@$%^&*_]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수 문자(~!@$%^&*_)를 포함해야 합니다.")
    private String password;


    @Pattern(regexp = "^01\\d{9}$",
            message = "전화번호는 01012345678 형식이어야 합니다.")
    private String phoneNumber;


    @Schema(description = "담당자명")
    private String managerName;


    @Schema(description = "업체명")
    private String companyName;


    @Schema(description = "서비스 유형")
    private String businessType;


    @Enumerated(EnumType.STRING)
    @Schema(description = "개인사업자, 법인사업자, 공공기관")
    private PartnerType partnerType;
}
