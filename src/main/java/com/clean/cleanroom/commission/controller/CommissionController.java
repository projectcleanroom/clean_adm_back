package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.service.CommissionService;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission")
public class CommissionController {

    private final CommissionService commissionService;
    private final TokenService tokenService;


    public CommissionController(CommissionService commissionService, TokenService tokenService) {
        this.commissionService = commissionService;
        this.tokenService = tokenService;
    }

    //청소의뢰 생성
    @PostMapping
    public ResponseEntity<List<CommissionCreateResponseDto>> createCommission(HttpServletRequest request, @RequestBody CommissionCreateRequestDto requestDto) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<CommissionCreateResponseDto> responseDtoList = commissionService.createCommission(email, requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.CREATED);
    }


    //청소의뢰 수정
    @PutMapping
    public ResponseEntity<List<CommissionUpdateResponseDto>> updateCommission(
            HttpServletRequest request,
            @RequestParam Long commissionId,
            @RequestParam Long addressId,
            @RequestBody CommissionUpdateRequestDto requestDto) {

        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출
        List<CommissionUpdateResponseDto> responseDtoList = commissionService.updateCommission(email, commissionId, addressId, requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //청소의뢰 취소
    @DeleteMapping
    public ResponseEntity<List<CommissionCancelResponseDto>> cancelCommission(HttpServletRequest request, @RequestParam Long commissionId) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<CommissionCancelResponseDto> responseDtoList = commissionService.cancelCommission(email, commissionId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    // 내 청소의뢰내역 조회
    @GetMapping
    public ResponseEntity<List<MyCommissionResponseDto>> getMyCommission(HttpServletRequest request) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<MyCommissionResponseDto> responseDtoList = commissionService.getMemberCommissionsByEmail(email, MyCommissionResponseDto.class);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


}

