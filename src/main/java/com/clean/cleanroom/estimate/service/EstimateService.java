package com.clean.cleanroom.estimate.service;

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

        // Members와 Address 정보 가져오기
        Members members = commission.getMembers();
        Address address = commission.getAddress();

        //견적 생성
        Estimate estimate = new Estimate(estimateCreateRequestDto, commission, partner);

        //레포지토리에 저장
        estimateRepository.save(estimate);

        // 새로운 DTO 반환
        return new EstimateCreateResponseDto(estimate, members, address, commission);
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

        Members members = commission.getMembers();
        Address address = commission.getAddress();

        return new EstimateUpdateResponseDto(estimate, members, address, commission);
    }


    // 가격 수정
    public EstimatePatchResponseDto patchEstimate(String token,
                                                  Long id,
                                                  EstimatePatchRequestDto estimatePatchRequestDto) {
        String email = jwtUtil.extractEmail(token);

        Partner partner = getPartnerByEmail(email);

        // 해당 견적서 찾기
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        // 요청한 파트너와 견적서의 파트너가 일치하는지 확인
        if (!estimate.getPartner().equals(partner)) {
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }

        // `patchEstimate` 메서드를 사용하여 가격만 업데이트
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

        // 각 Estimate에 대해 DTO 변환
        for (Estimate estimate : estimates) {
            Commission commission = estimate.getCommission();
            Members members = commission.getMembers();
            Address address = commission.getAddress();

            // DTO에 필요한 정보를 전달하여 객체 생성
            EstimateListResponseDto dto = new EstimateListResponseDto(estimate, members, address, commission);
            estimateListResponseDtos.add(dto);
        }

        return estimateListResponseDtos;
    }


    // 회원에게 발송한 확정 견적 내역 조회
    public List<EstimateConfirmListResponseDto> getAllConfirmEstimates(String token) {

        String email = jwtUtil.extractEmail(token);
        Partner partner = getPartnerByEmail(email);

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




    //이메일로 파트너 찾는 메서드
    private Partner getPartnerByEmail(String email){
        return partnerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }
}


