package com.clean.cleanroom.account.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}