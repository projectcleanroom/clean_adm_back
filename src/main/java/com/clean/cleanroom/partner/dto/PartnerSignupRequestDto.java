package com.clean.cleanroom.partner.dto;

import com.clean.cleanroom.enums.PartnerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PartnerSignupRequestDto {

    @Email
    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    private String eamil;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@$%^&*_])[a-zA-Z\\d~!@$%^&*_]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수 문자(~!@$%^&*_)를 포함해야 합니다.")
    private String password;

    @NotEmpty(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01\\d{9}$",
            message = "전화번호는 01012345678 형식이어야 합니다.")
    private String phoneNumber;

    @NotEmpty(message = "담당자명은 필수 입력 항목입니다.")
    @Schema(description = "담당자명")
    private String managerName;

    @NotEmpty(message = "업체명은 필수 입력 항목입니다.")
    @Schema(description = "업체명")
    private String companyName;

    @NotEmpty(message = "서비스 유형은 필수 입력 항목입니다.")
    @Schema(description = "서비스 유형")
    private String businessType;

    @NotEmpty(message = "필수 입력 항목입니다.")
    @Enumerated (EnumType.STRING)
    @Schema(description = "개인사업자, 법인사업자, 공공기관")
    private PartnerType partnerType;

}
