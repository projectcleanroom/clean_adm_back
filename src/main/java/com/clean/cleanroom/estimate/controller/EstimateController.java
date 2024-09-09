package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.*;
import com.clean.cleanroom.estimate.service.EstimateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping ("/partner/estimate")
@Tag(name = "청소 견적")
public class EstimateController {

    private EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }


    // 견적서 작성
    @PostMapping
    public ResponseEntity<EstimateCreateResponseDto> createEstimate(@RequestHeader("Authorization") String token,
                                                                    @RequestBody EstimateCreateRequestDto estimateCreateRequestDto) {
        EstimateCreateResponseDto estimateCreateResponseDto = estimateService.createEstimate(token, estimateCreateRequestDto);
        return new ResponseEntity<>(estimateCreateResponseDto, HttpStatus.CREATED);
    }


    // 견적서 수정
    @PutMapping
    public ResponseEntity<EstimateUpdateResponseDto> updateEstimate(@RequestHeader("Authorization") String token,
                                                                    @RequestParam Long id,
                                                                    @RequestBody EstimateUpdateRequestDto estimateUpdateRequestDto) {
        EstimateUpdateResponseDto estimateUpdateResponseDto = estimateService.updateEstimate(token, id, estimateUpdateRequestDto);
        return new ResponseEntity<>(estimateUpdateResponseDto, HttpStatus.OK);
    }


    @PatchMapping
    public ResponseEntity<EstimatePatchResponseDto> patchEstimate(@RequestHeader("Authorization") String token,
                                                                  @RequestParam Long id,
                                                                  @RequestBody EstimatePatchRequestDto estimatePatchRequestDto) {
        EstimatePatchResponseDto responseDto = estimateService.patchEstimate(token, id, estimatePatchRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 견적서 삭제
    @DeleteMapping
    public ResponseEntity<EstimateDeleteResponseDto> deleteEstimate(@RequestHeader("Authorization") String token,
                                                                    @RequestParam Long id) {
        EstimateDeleteResponseDto responseDto = estimateService.deleteEstimate(token, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 파트너 본인이 작성한 견적 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<EstimateListResponseDto>> getAllEstimatesForPartner(@RequestHeader("Authorization") String token) {
        List<EstimateListResponseDto> estimateListResponseDtos = estimateService.getAllEstimatesForPartner(token);
        return new ResponseEntity<>(estimateListResponseDtos, HttpStatus.OK);
    }

    // 확정가격 발송한 견적 내역 조회
    @GetMapping("/confirmlist")
    public ResponseEntity<List<EstimateConfirmListResponseDto>> getAllConfirmEstimates(@RequestHeader("Authorization") String token) {
        List<EstimateConfirmListResponseDto> estimateConfirmListResponseDtos = estimateService.getAllConfirmEstimates(token);
        return new ResponseEntity<>(estimateConfirmListResponseDtos, HttpStatus.OK);
    }
}
