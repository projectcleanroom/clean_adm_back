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
import com.clean.cleanroom.redis.RedisService;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EstimateService {


    private final EstimateRepository estimateRepository;
    private final PartnerRepository partnerRepository;
    private final CommissionRepository commissionRepository;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public EstimateService(EstimateRepository estimateRepository,
                           PartnerRepository partnerRepository,
                           CommissionRepository commissionRepository,
                           JwtUtil jwtUtil,
                           RedisService redisService) {
        this.estimateRepository = estimateRepository;
        this.partnerRepository = partnerRepository;
        this.commissionRepository = commissionRepository;
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
    }


    //견적서 작성
    public EstimateCreateResponseDto createEstimate (String token, EstimateCreateRequestDto estimateCreateRequestDto) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        // 의뢰 찾기
        CommissionEstimateDetailsDto commissionDetails =
                commissionRepository
                        .findCommissionDetailsById(estimateCreateRequestDto.getCommissionId())
                        .map(CommissionRepository.CommissionProjection::toDto)
                        .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        //견적 생성
        Estimate estimate = new Estimate(estimateCreateRequestDto, commissionDetails.getCommissionId(), partner);

        //레포지토리에 저장
        estimateRepository.save(estimate);

        return new EstimateCreateResponseDto(estimate, commissionDetails);
    }


    //견적서 수정
    public EstimateUpdateResponseDto updateEstimate (String token,
                                                     Long id,
                                                     EstimateUpdateRequestDto estimateUpdateRequestDto) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        // 견적 식별값으로 해당 견적 내역 찾기
        Estimate estimate = getEstimateById(id);

        // 토큰 파트너와 견적 파트너가 일치하는지 확인
        validatePartner(estimate, partner);

        //requestDto에서 의뢰 식별값 가져와서 해당 의뢰 내역 찾기
        Commission commission = commissionRepository.findById(estimateUpdateRequestDto.getCommissionId())
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        estimate.updateEstimate(estimateUpdateRequestDto, commission, partner);

        estimateRepository.save(estimate);

        Members members = commission.getMembers();
        Address address = commission.getAddress();

        return new EstimateUpdateResponseDto(estimate, members, address, commission);
    }


    // 가격 수정
    public EstimatePatchResponseDto patchEstimate(String token,
                                                  Long id,
                                                  EstimatePatchRequestDto estimatePatchRequestDto) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        // 견적 식별값으로 해당 견적 내역 찾기
        Estimate estimate = getEstimateById(id);

        // 토큰 파트너와 견적 파트너가 일치하는지 확인
        validatePartner(estimate, partner);

        // patchEstimate 메서드를 사용하여 가격만 업데이트
        estimate.patchEstimate(estimatePatchRequestDto, estimate.getCommission(), partner);

        // 업데이트된 견적서를 저장
        estimateRepository.save(estimate);

        // 응답 DTO 생성
        Members members = estimate.getCommission().getMembers();
        Address address = estimate.getCommission().getAddress();
        Commission commission = estimate.getCommission();

        return new EstimatePatchResponseDto(estimate, members, address, commission);
    }


    //견적서 삭제
    public EstimateDeleteResponseDto deleteEstimate(String token, Long id) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        // 견적 식별값으로 해당 견적 내역 찾기
        Estimate estimate = getEstimateById(id);

        // 토큰 파트너와 견적 파트너가 일치하는지 확인
        validatePartner(estimate, partner);

        estimateRepository.delete(estimate);

        return new EstimateDeleteResponseDto(estimate);
    }


    //파트너 견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimatesForPartner(String token) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);
        String redisKey = "partner_estimates:" + partner.getId();

        // Redis에서 데이터를 조회
        List<EstimateListResponseDto> estimateListResponseDtos =
                redisService.getObject(redisKey, List.class);
        System.out.println("================캐싱 적용");

        // Redis에 데이터가 있다면 바로 반환
        if (estimateListResponseDtos != null && !estimateListResponseDtos.isEmpty()) {
            return estimateListResponseDtos;
        }

        // Redis에 데이터가 없는 경우, DB에서 조회
        List<Estimate> estimates = estimateRepository.findByPartnerId(partner.getId());

        // 견적 내역이 존재하지 않으면 예외 발생
        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND);
        }

        // DTO 리스트 생성 및 변환
        estimateListResponseDtos = new ArrayList<>();

        // 각 Estimate에 대해 DTO 변환
        for (Estimate estimate : estimates) {
            Commission commission = estimate.getCommission();
            Members members = commission.getMembers();
            Address address = commission.getAddress();

            // DTO에 필요한 정보를 전달하여 객체 생성
            EstimateListResponseDto dto = new EstimateListResponseDto(estimate, members, address, commission);
            estimateListResponseDtos.add(dto);
        }

        // 데이터를 Redis에 캐싱 (30분 동안 유지)
        redisService.setObject(redisKey, estimateListResponseDtos, 30, TimeUnit.MINUTES);

        return estimateListResponseDtos;
    }


    // 회원에게 발송한 확정 견적 내역 조회
    public List<EstimateConfirmListResponseDto> getAllConfirmEstimates(String token) {

        // 토큰으로 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        //findByPartnerIdAndStatusIn에서 In은 필드가 여러개일 때 사용함
        List<Estimate> estimates = estimateRepository.findByPartnerIdAndStatusIn (
                partner.getId(), List.of(StatusType.SEND, StatusType.FINISH)
        );

        // 견적 내역이 존재하지 않으면 예외 발생
        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND);
        }

        List<EstimateConfirmListResponseDto> estimateConfirmListResponseDtos = new ArrayList<>();

        // 각 Estimate에 대해 DTO 변환
        for (Estimate estimate : estimates) {
            Commission commission = estimate.getCommission();
            Members members = commission.getMembers();
            Address address = commission.getAddress();

            // DTO에 필요한 정보를 전달하여 객체 생성
            EstimateConfirmListResponseDto dto = new EstimateConfirmListResponseDto(estimate, members, address, commission);
            estimateConfirmListResponseDtos.add(dto);
        }

        return estimateConfirmListResponseDtos;
    }



    // 토큰에서 이메일 추출
    private Partner getPartnerFromToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return getPartnerByEmail(email);
    }

    //이메일로 파트너 찾는 메서드
    private Partner getPartnerByEmail (String email){
        return partnerRepository.findActiveByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }

    // 견적 ID로 견적 찾기
    private Estimate getEstimateById (Long id) {
        return estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));
    }

    // 파트너와 견적 일치 확인
    private void validatePartner (Estimate estimate, Partner partner) {
        if (!estimate.getPartner().equals(partner)) {
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }
    }
}