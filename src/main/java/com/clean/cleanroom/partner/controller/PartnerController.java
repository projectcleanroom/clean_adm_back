package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.PartnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
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

    @PostMapping("/login")
    public ResponseEntity<PartnerLoginResponseDto> login (@RequestBody PartnerLoginRequestDto partnerLoginRequestDto) {
        PartnerLoginResponseDto partnerLoginResponseDto = partnerService.login(partnerLoginRequestDto);
        return new ResponseEntity<>(partnerLoginResponseDto, HttpStatus.CREATED);
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


    @PutMapping("/logout")
    public ResponseEntity<PartnerLogoutResponseDto> logout (@RequestHeader("Authorization") String accessToken,
                                                            @RequestHeader("Refresh-Token") String refreshToken) {
        PartnerLogoutResponseDto partnerLogoutResponseDto = partnerService.logout(accessToken, refreshToken);
        return new ResponseEntity<>(partnerLogoutResponseDto, HttpStatus.CREATED);
    }

}
