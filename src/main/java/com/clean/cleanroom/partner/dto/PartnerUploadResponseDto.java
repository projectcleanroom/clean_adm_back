package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerUploadResponseDto {

    @Schema(description = "파일 제목")
    private String fileName;

    @Schema(description = "메세지")
    private String message;

    public PartnerUploadResponseDto(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}