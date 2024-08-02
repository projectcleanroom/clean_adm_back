package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;

@Getter
public class MembersSignupResponseDto {
    private String message;

    public MembersSignupResponseDto(Members members) {
        this.message = "회원 가입 성공!";
    }

}
