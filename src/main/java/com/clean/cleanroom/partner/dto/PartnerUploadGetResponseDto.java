package com.clean.cleanroom.partner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PartnerUploadGetResponseDto {

    @Schema(description = "파일")
    private String file;

    @Schema(description = "메세지")
    private String message;

    @Schema(description = "파일 업로드 날짜")
    private byte[] fileData;

    public PartnerUploadGetResponseDto(String file, String message, byte[] fileData) {
        this.file = file;
        this.message = message;
        this.fileData = fileData;
    }
}
