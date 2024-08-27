package com.clean.cleanroom.partner.dto;

import lombok.Getter;

@Getter
public class PartnerUploadGetResponseDto {
    private String file;
    private String message;
    private byte[] fileData;

    public PartnerUploadGetResponseDto(String file, String message, byte[] fileData) {
        this.file = file;
        this.message = message;
        this.fileData = fileData;
    }
}
