package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersLoginRequestDto {
    private String email;
    private String password;
}