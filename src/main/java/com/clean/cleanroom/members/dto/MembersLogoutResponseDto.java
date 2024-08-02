package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersLogoutResponseDto {
    private String message;

    public MembersLogoutResponseDto(String message) {
        this.message = message;
    }
}
