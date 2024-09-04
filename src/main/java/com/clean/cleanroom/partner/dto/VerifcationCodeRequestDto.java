package com.clean.cleanroom.partner.dto;

import lombok.Getter;

@Getter
public class VerifcationCodeRequestDto {
    private String email;
    private String code;
}