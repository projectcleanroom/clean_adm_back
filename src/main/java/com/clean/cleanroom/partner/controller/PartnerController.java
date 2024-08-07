package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.PartnerService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PartnerSignupResponseDto> signup (@RequestBody @Valid PartnerRequestDto requestDto) {
        PartnerSignupResponseDto partnerSignupResponseDto = partnerService.signup(requestDto);
        return new ResponseEntity<>(partnerSignupResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/profile")
    public ResponseEntity<PartnerProfileResponseDto> profile (@RequestHeader("Authorization") String token, @RequestBody @Valid PartnerRequestDto requestDto) {
        PartnerProfileResponseDto partnerProfileResponseDto = partnerService.profile(token, requestDto);
        return new ResponseEntity<>(partnerProfileResponseDto, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<PartnerGetProfileResponseDto> getProfile(@RequestHeader("Authorization") String token) {
        PartnerGetProfileResponseDto partnerGetProfileResponseDto = partnerService.getProfile(token);
        return new ResponseEntity<>(partnerGetProfileResponseDto, HttpStatus.OK);
    }
}
