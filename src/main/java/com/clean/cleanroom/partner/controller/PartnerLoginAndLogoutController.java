package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.PartnerLoginRequestDto;
import com.clean.cleanroom.partner.dto.PartnerLoginResponseDto;
import com.clean.cleanroom.partner.dto.PartnerLogoutResponseDto;
import com.clean.cleanroom.partner.service.PartnerLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class PartnerLoginAndLogoutController {

    private final PartnerLoginService partnerLoginService;

    public PartnerLoginAndLogoutController(PartnerLoginService partnerLoginService) {
        this.partnerLoginService = partnerLoginService;

    }

    @PostMapping("/login")
    public ResponseEntity<PartnerLoginResponseDto> login(@RequestBody PartnerLoginRequestDto requestDto) {
        return partnerLoginService.login(requestDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<PartnerLogoutResponseDto> logout(@RequestHeader("Authorization") String accessToken, @RequestHeader("Refresh-Token") String refreshToken) {
        return partnerLoginService.logout(accessToken, refreshToken);
    }
}

