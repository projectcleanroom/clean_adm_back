package com.clean.cleanroom.commission.controller;


import com.clean.cleanroom.commission.dto.CommissionListResponseDto;
import com.clean.cleanroom.commission.service.CommissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/partner")
@Tag(name = "청소 의뢰")
public class CommissionController {

    private final CommissionService commissionService;

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    // 의뢰 전체 조회
    @GetMapping("/commissionlist")
    public ResponseEntity<List<CommissionListResponseDto>> getAllCommissions(@RequestHeader("Authorization") String token) {
        List<CommissionListResponseDto> commissionList = commissionService.getAllCommissions(token);
        return new ResponseEntity<>(commissionList, HttpStatus.OK);
    }
}
