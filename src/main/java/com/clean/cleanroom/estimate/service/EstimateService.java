package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final CommissionRepository commissionRepository;
    private final MembersRepository membersRepository;

    public EstimateService(EstimateRepository estimateRepository,
                           CommissionRepository commissionRepository,
                           MembersRepository membersRepository) {
        this.estimateRepository = estimateRepository;
        this.commissionRepository = commissionRepository;
        this.membersRepository = membersRepository;
    }


    //견적 승인
    public EstimateResponseDto approveEstimate(String email, Long id) {

        //email로 회원 찾기
        Members members = getMemberByEmail(email);

        //견적 내역 찾기
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        Commission commission = estimate.getCommissionId(); // 견적에 연결된 Commission 가져오기
        Members owner = commission.getMembers(); // Commission에 연결된 회원 가져오기

        if (!owner.getId().equals(members.getId())) { // 견적의 소유자가 요청한 회원인지 확인
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        estimateRepository.save(estimate); //estimate 레포지토리에 저장

        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate); //estimate 객체를 Dto로 변환

        return estimateResponseDto;
    }


    //견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimates(String email, Long commissionId) {

        //email로 회원 찾기
        Members members = getMemberByEmail(email);

        //의뢰 내역 찾기
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        //의뢰 객체에서 member 필드 가져오기
        Members owner = commission.getMembers();

        if (!owner.getId().equals(members.getId())) { // 견적의 소유자가 요청한 회원인지 확인
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        //commission에 해당하는 견적들을 리스트에 담기
        List<Estimate> estimates = estimateRepository.findByCommissionId(commission);

        //견적이 비어있는지 확인
        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.NO_ESTIMATES_FOUND);
        }

        //DTO로 반환하기 위해 변환
        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();

        for (Estimate estimate : estimates) {
            EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);
            estimateListResponseDtos.add(estimateListResponseDto);
        }
        return estimateListResponseDtos;
    }



    //email로 회원 찾기
    private Members getMemberByEmail(String email) {
        return membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));
    }

}


