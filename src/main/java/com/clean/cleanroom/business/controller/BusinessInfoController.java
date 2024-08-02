package com.clean.cleanroom.business.controller;

import com.clean.cleanroom.business.dto.BusinessInfoRequestDto;
import com.clean.cleanroom.business.dto.BusinessInfoResponseDto;
import com.clean.cleanroom.business.dto.BusinessInfoUpdateRequestDto;
import com.clean.cleanroom.business.dto.BusinessInfoUpdateResponseDto;
import com.clean.cleanroom.business.service.BusinessInfoService;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/businessInfo")
public class BusinessInfoController {

    private final BusinessInfoService businessInfoService;
    private final TokenService tokenService;

    public BusinessInfoController(BusinessInfoService businessInfoService, TokenService tokenService) {
        this.businessInfoService = businessInfoService;
        this.tokenService = tokenService;
    }


    //사업자 등록
    @PostMapping
    public ResponseEntity<BusinessInfoResponseDto> createBusinessInfo(@RequestBody BusinessInfoRequestDto requestDto, HttpServletRequest request) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        BusinessInfoResponseDto responseDto = businessInfoService.createBusinessInfo(email, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //사업자 등록 수정
    @PutMapping
    public ResponseEntity<BusinessInfoUpdateResponseDto> updateBusinessInfo(
            @RequestParam Long businessInfoId,
            @RequestBody BusinessInfoUpdateRequestDto requestDto,
            HttpServletRequest request) {

        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        BusinessInfoUpdateResponseDto responseDto = businessInfoService.updateBusinessInfo(email, businessInfoId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
