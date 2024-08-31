package com.clean.cleanroom.estimate.service;
import com.clean.cleanroom.commission.dto.CommissionEstimateDetailsDto;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.*;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final CommissionRepository commissionRepository;
    private final JwtUtil jwtUtil;
    private final PartnerRepository partnerRepository;



    // 견적서 작성 (공간복잡도 개선)
    public EstimateCreateResponseDto createEstimate(String token, EstimateCreateRequestDto estimateCreateRequestDto) {

        // 토큰에서 이메일 추출
        String email = jwtUtil.extractEmail(token);

        // 이메일로 파트너 찾기
        Partner partner = partnerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.PARTNER_NOT_FOUND));

        // Commission과 연관된 필요한 데이터만 가져오기
        CommissionEstimateDetailsDto commissionDetails = commissionRepository.findCommissionDetailsById(estimateCreateRequestDto.getCommissionId())
                .map(CommissionRepository.CommissionProjection::toDto)
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        // Estimate 생성 및 저장
        Estimate estimate = createAndSaveEstimate(estimateCreateRequestDto, commissionDetails, partner);

        // DTO 생성 및 반환
        return createEstimateResponseDto (estimate, commissionDetails);
    }



    // 견적서 수정
    public EstimateUpdateResponseDto updateEstimate(String token,
                                                    Long id,
                                                    EstimateUpdateRequestDto estimateUpdateRequestDto) {
        String email = extractEmailFromToken(token);
        Partner partner = findPartnerByEmail(email);
        Estimate estimate = findEstimateById(id);

        // 견적과 파트너가 일치하는지 확인
        validatePartnerForEstimate(estimate, partner);

        Commission commission = findCommissionById(estimateUpdateRequestDto.getCommissionId());

        estimate.updateEstimate(estimateUpdateRequestDto, commission, partner);

        estimateRepository.save(estimate);

        return createEstimateUpdateResponseDto(estimate, commission);
    }



    // 가격 수정
    public EstimatePatchResponseDto patchEstimate(String token,
                                                  Long id,
                                                  EstimatePatchRequestDto estimatePatchRequestDto) {

        String email = extractEmailFromToken(token);
        Partner partner = findPartnerByEmail(email);
        Estimate estimate = findEstimateById(id);
        validatePartnerForEstimate(estimate, partner);
        estimate.patchEstimate(estimatePatchRequestDto, estimate.getCommission(), partner);
        estimateRepository.save(estimate);

        return createEstimatePatchResponseDto(estimate);
    }



    // 견적서 삭제
    public EstimateDeleteResponseDto deleteEstimate(String token, Long id) {

        String email = extractEmailFromToken(token);
        Partner partner = findPartnerByEmail(email);
        Estimate estimate = findEstimateById(id);
        validatePartnerForEstimate(estimate, partner);
        estimateRepository.delete(estimate);

        return createEstimateDeleteResponseDto(estimate);
    }



    // 파트너 견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimatesForPartner(String token) {

        String email = extractEmailFromToken(token);
        Partner partner = findPartnerByEmail(email);

        List<Estimate> estimates = estimateRepository.findByPartnerId(partner.getId());

        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND);
        }

        return createEstimateListResponseDtos(estimates);
    }



    // 회원에게 발송한 확정 견적 내역 조회
    public List<EstimateConfirmListResponseDto> getAllConfirmEstimates(String token) {

        String email = extractEmailFromToken(token);
        Partner partner = findPartnerByEmail(email);

        List<Estimate> estimates = estimateRepository.findByPartnerIdAndStatusIn(
                partner.getId(), List.of(StatusType.SEND, StatusType.FINISH)
        );

        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND);
        }

        return createEstimateConfirmListResponseDtos(estimates);
    }




    // 토큰에서 이메일을 추출
    private String extractEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }

    // 이메일로 파트너 찾기
    private Partner findPartnerByEmail(String email) {
        return partnerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }

    // 의뢰 식별값으로 commission 내역 찾기
    private Commission findCommissionById(Long commissionId) {
        return commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));
    }

    // 견적 식별값으로 estimate 내역 찾기
    private Estimate findEstimateById(Long estimateId) {
        return estimateRepository.findById(estimateId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));
    }

    // 견적과 파트너가 일치하는지 확인
    private void validatePartnerForEstimate(Estimate estimate, Partner partner) {
        if (!estimate.getPartner().equals(partner)) {
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }
    }

    // 견적 생성, 저장
    private Estimate createAndSaveEstimate(EstimateCreateRequestDto dto,
                                           CommissionEstimateDetailsDto commissionDetails,
                                           Partner partner) {
        // 새로운 생성자를 사용하여 Estimate 객체 생성
        Estimate estimate = new Estimate(dto, commissionDetails.getCommissionId(), partner);
        return estimateRepository.save(estimate);
    }

    // EstimateCreateResponseDto 생성
    private EstimateCreateResponseDto createEstimateResponseDto(Estimate estimate, CommissionEstimateDetailsDto commissionDetails) {
        return new EstimateCreateResponseDto(estimate, commissionDetails);
    }

    // EstimateUpdateResponseDto 생성
    private EstimateUpdateResponseDto createEstimateUpdateResponseDto(Estimate estimate, Commission commission) {
        Members members = commission.getMembers();
        Address address = commission.getAddress();
        return new EstimateUpdateResponseDto(estimate, members, address, commission);
    }

    // EstimatePatchResponseDto 생성
    private EstimatePatchResponseDto createEstimatePatchResponseDto(Estimate estimate) {
        Members members = estimate.getCommission().getMembers();
        Address address = estimate.getCommission().getAddress();
        Commission commission = estimate.getCommission();
        return new EstimatePatchResponseDto(estimate, members, address, commission);
    }

    // EstimateDeleteResponseDto 생성
    private EstimateDeleteResponseDto createEstimateDeleteResponseDto(Estimate estimate) {
        return new EstimateDeleteResponseDto(estimate);
    }

    // EstimateListResponseDto 리스트 생성
    private List<EstimateListResponseDto> createEstimateListResponseDtos(List<Estimate> estimates) {
        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();
        for (Estimate estimate : estimates) {
            Commission commission = estimate.getCommission();
            Members members = commission.getMembers();
            Address address = commission.getAddress();
            estimateListResponseDtos.add(new EstimateListResponseDto(estimate, members, address, commission));
        }
        return estimateListResponseDtos;
    }

    // EstimateConfirmListResponseDto 리스트 생성
    private List<EstimateConfirmListResponseDto> createEstimateConfirmListResponseDtos(List<Estimate> estimates) {
        List<EstimateConfirmListResponseDto> estimateConfirmListResponseDtos = new ArrayList<>();
        for (Estimate estimate : estimates) {
            Commission commission = estimate.getCommission();
            Members members = commission.getMembers();
            Address address = commission.getAddress();
            estimateConfirmListResponseDtos.add(new EstimateConfirmListResponseDto(estimate, members, address, commission));
        }
        return estimateConfirmListResponseDtos;
    }
}