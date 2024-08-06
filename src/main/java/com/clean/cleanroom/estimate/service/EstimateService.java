package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.*;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {


    private final EstimateRepository estimateRepository;
    private final PartnerRepository partnerRepository;
    private final CommissionRepository commissionRepository;
    private final JwtUtil jwtUtil;

    public EstimateService(EstimateRepository estimateRepository,
                           PartnerRepository partnerRepository,
                           CommissionRepository commissionRepository,
                           JwtUtil jwtUtil) {
        this.estimateRepository = estimateRepository;
        this.partnerRepository = partnerRepository;
        this.commissionRepository = commissionRepository;
        this.jwtUtil = jwtUtil;
    }


    //견적서 작성
    public EstimateCreateResponseDto createEstimate (String token, EstimateCreateRequestDto estimateCreateRequestDto) {

        //토큰에서 이메일 추출
        String email = jwtUtil.extractEmail(token);

        //이메일로 파트너 찾기
        Partner partner = getPartnerByEmail(email);

        //requestDto에서 의뢰 식별값 가져와서 해당하는 의뢰 찾기
        Commission commission = commissionRepository.findById(estimateCreateRequestDto.getCommissionId())
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        //견적 생성
        Estimate estimate = new Estimate(estimateCreateRequestDto, commission, partner);

        //레포지토리에 저장
        estimateRepository.save(estimate);

        return new EstimateCreateResponseDto (
                estimate.getCommissionId().getId(),
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

        //견적 식별값으로 해당 견적 내역 찾기
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        //찾은 견적 내역에서 파트너 필드랑 이메일로 찾은 파트너랑 같은지 확인
        if (!estimate.getPartner().equals(partner)){
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }

        //requestDto에서 의뢰 식별값 가져와서 해당 의뢰 내역 찾기
        Commission commission = commissionRepository.findById(estimateUpdateRequestDto.getCommissionId())
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        estimate.updateEstimate(estimateUpdateRequestDto, commission, partner);

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


    //파트너 견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimatesForPartner (String token) {

        //토큰에서 email 추출
        String email = jwtUtil.extractEmail(token);

        //email로 파트너 찾기
        Partner partner = getPartnerByEmail(email);

        List<Estimate> estimates = estimateRepository.findByPartnerId(partner.getId());

        // 견적 내역이 존재하지 않으면 예외 발생
        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND);
        }

        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();

        for (Estimate estimate : estimates) {
            estimateListResponseDtos.add(new EstimateListResponseDto(estimate));
        }

        return estimateListResponseDtos;
    }


    //이메일로 파트너 찾는 메서드
    private Partner getPartnerByEmail(String email){
        return partnerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }
}


