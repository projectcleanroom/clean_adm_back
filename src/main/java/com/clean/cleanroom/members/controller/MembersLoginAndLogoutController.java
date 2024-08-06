package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.service.MembersLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MembersLoginAndLogoutController {

    private final MembersLoginService membersService;

    @PostMapping("/login")
    public ResponseEntity<MembersLoginResponseDto> login(@RequestBody MembersLoginRequestDto requestDto) {
        return membersService.login(requestDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<MembersLogoutResponseDto> logout(@RequestHeader("Authorization") String accessToken, @RequestHeader("Refresh-Token") String refreshToken) {
        return membersService.logout(accessToken, refreshToken);
    }
}