package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembersLoginResponseDto {
    private String email;
    private String nick;
    private String message;

    public MembersLoginResponseDto(Members members) {
        this.email = members.getEmail();
        this.nick = members.getNick();
    }

    public MembersLoginResponseDto(String message) {
        this.message = message;
    }
}