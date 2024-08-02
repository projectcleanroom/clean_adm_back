package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.service.EstimateService;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private final EstimateService estimateService;
    private final JwtUtil jwtUtil;

    public EstimateController(EstimateService estimateService, JwtUtil jwtUtil) {
        this.estimateService = estimateService;
        this.jwtUtil = jwtUtil;
    }


    //견적 승인
    @PostMapping
    public ResponseEntity<EstimateResponseDto> approveEstimate (HttpServletRequest request, @RequestParam Long id) {
        String email = getEmailFromRequest(request); // 헤더에서 email 추출하는 메서드 호출
        EstimateResponseDto estimateResponseDto = estimateService.approveEstimate(email, id);
        return new ResponseEntity<>(estimateResponseDto, HttpStatus.OK);
    }


    //내 견적 전체 조회
    @GetMapping
    public ResponseEntity<List<EstimateListResponseDto>> getAllEstimates(HttpServletRequest request, Long id) {
        String email = getEmailFromRequest(request);
        List<EstimateListResponseDto> estimateListResponseDtos = estimateService.getAllEstimates(email, id);
        return new ResponseEntity<>(estimateListResponseDtos, HttpStatus.OK);
    }


    //요청헤더에서 토큰안의 이메일을 추출하는 메서드
    private String getEmailFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return jwtUtil.getEmailFromToken(token);
    }
}
