package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.PartnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partner")
public class PartnerLoginAndLogoutController {

    private final PartnerService partnerService;

    public PartnerLoginAndLogoutController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("/login")
    public ResponseEntity<PartnerLoginResponseDto> login (@RequestBody PartnerLoginRequestDto partnerLoginRequestDto) {
        PartnerLoginResponseDto partnerLoginResponseDto = partnerService.login(partnerLoginRequestDto);
        return new ResponseEntity<>(partnerLoginResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/logout")
    public ResponseEntity<PartnerLogoutResponseDto> logout (@RequestHeader("Authorization") String accessToken,
                                                            @RequestHeader("Refresh-Token") String refreshToken) {
        PartnerLogoutResponseDto partnerLogoutResponseDto = partnerService.logout(accessToken, refreshToken);
        return new ResponseEntity<>(partnerLogoutResponseDto, HttpStatus.CREATED);
    }

}

