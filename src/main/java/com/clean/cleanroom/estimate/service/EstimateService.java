package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.estimate.dto.*;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class EstimateService {


    private final EstimateRepository estimateRepository;
    private final PartnerRepository partnerRepository;
    private final JwtUtil jwtUtil;

    public EstimateService(EstimateRepository estimateRepository,
                           PartnerRepository partnerRepository,
                           JwtUtil jwtUtil) {
        this.estimateRepository = estimateRepository;
        this.partnerRepository = partnerRepository;
        this.jwtUtil = jwtUtil;
    }


    //견적서 작성
    public EstimateCreateResponseDto createEstimate (String token, EstimateCreateRequestDto estimateCreateRequestDto) {

        String email = jwtUtil.extractEmail(token);

        Partner partner = getPartnerByEmail(email);

        Estimate estimate = new Estimate(estimateCreateRequestDto, partner);

        estimateRepository.save(estimate);

        return new EstimateCreateResponseDto (
                estimate.getCommissionId(),
                estimate.getPartner().getId(),
                estimate.getPrice(),
                estimate.getFixedDate(),
                estimate.getStatement()
        );
    }



    //견적서 수정
    public EstimateUpdateResponseDto updateEstimate (String token,
                                                     Long id,
                                                     EstimateUpdateRequestDto estimateUpdateRequestDto) {

        String email = jwtUtil.extractEmail(token);

        Partner partner = getPartnerByEmail(email);

        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        if (!estimate.getPartner().equals(estimate)){
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }

        estimate.updateEstimate(estimateUpdateRequestDto, partner);

        estimateRepository.save(estimate);

        return new EstimateUpdateResponseDto(
                estimate.getId(),
                estimate.getCommissionId(),
                estimate.getPartner().getId(),
                estimate.getPrice(),
                estimate.getStatement(),
                estimate.getFixedDate()
        );
    }


    //견적서 삭제
    public EstimateDeleteResponseDto deleteEstimate(String token, Long id) {

        String email = jwtUtil.extractEmail(token);

        Partner partner = getPartnerByEmail(email);

        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        if (!estimate.getPartner().equals(partner)) {
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }

        estimateRepository.delete(estimate);

        return new EstimateDeleteResponseDto(estimate);
    }


    //이메일로 파트너 찾는 메서드
    private Partner getPartnerByEmail(String email){
        return partnerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }
}


