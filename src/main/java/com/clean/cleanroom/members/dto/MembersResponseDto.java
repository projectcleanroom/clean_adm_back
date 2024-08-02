package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nick;
    private String phoneNumber;

}
