package com.clean.cleanroom.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MessageResponseDto {

    @Schema(description = "메세지")
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}