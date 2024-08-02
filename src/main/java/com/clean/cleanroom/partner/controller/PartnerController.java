package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partner")
public class PartnerController {

    private final PartnerService partnerService;
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping ("/signup")
    public ResponseEntity<PartnerSignupResponseDto> signup (@RequestBody PartnerSignupRequestDto partnerSignupRequestDto) {
        PartnerSignupResponseDto partnerSignupResponseDto = partnerService.signup(partnerSignupRequestDto);
        return new ResponseEntity<>(partnerSignupResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<PartnerLoginResponseDto> login (@RequestBody PartnerLoginRequestDto partnerLoginRequestDto) {
        PartnerLoginResponseDto partnerLoginResponseDto = partnerService.login(partnerLoginRequestDto);
        return new ResponseEntity<>(partnerLoginResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PartnerUpdateResponseDto> update (@RequestParam Long id, @RequestBody PartnerUpdateRequestDto partnerUpdatepRequestDto) {
        PartnerUpdateResponseDto partnerUpdateResponseDto = partnerService.update(id, partnerUpdatepRequestDto);
        return new ResponseEntity<>(partnerUpdateResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/logout")
    public ResponseEntity<PartnerLogoutResponseDto> logout (@RequestHeader("Authorization") String accessToken,
                                                            @RequestHeader("Refresh-Token") String refreshToken) {
        PartnerLogoutResponseDto partnerLogoutResponseDto = partnerService.logout(accessToken, refreshToken);
        return new ResponseEntity<>(partnerLogoutResponseDto, HttpStatus.CREATED);
    }






}
