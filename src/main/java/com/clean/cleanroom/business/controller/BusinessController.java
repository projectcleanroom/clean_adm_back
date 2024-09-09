package com.clean.cleanroom.business.controller;

import com.clean.cleanroom.business.dto.*;
import com.clean.cleanroom.business.service.BusinessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
@Tag(name = "사업자")
public class BusinessController {

    private BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    // 사업자 인증
    @PostMapping("/validate")
    public ResponseEntity<BusinessValidateResponseDto> validateBusiness (@RequestBody BusinessValidateRequestDto businessValidateRequestDto) {
        BusinessValidateResponseDto businessValidateResponseDto = businessService.validatebusiness(businessValidateRequestDto);
        return new ResponseEntity<>(businessValidateResponseDto, HttpStatus.OK);
    }
}
