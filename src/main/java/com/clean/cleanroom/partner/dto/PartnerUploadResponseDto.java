package com.clean.cleanroom.partner.dto;

import lombok.Getter;

@Getter
public class PartnerUploadResponseDto {
    private String fileName;
    private String message;

    public PartnerUploadResponseDto(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}