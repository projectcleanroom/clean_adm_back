package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.CommissionListResponseDto;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionService {

    private final CommissionRepository commissionRepository;
    private final PartnerRepository partnerRepository;
    private final JwtUtil jwtUtil;

    public CommissionService(CommissionRepository commissionRepository,
                             PartnerRepository partnerRepository,
                             JwtUtil jwtUtil) {
        this.commissionRepository = commissionRepository;
        this.partnerRepository = partnerRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<CommissionListResponseDto> getAllCommissions(String token) {

        //토큰에서 이메일 추출
        String email = jwtUtil.extractEmail(token);

        //이메일로 파트너 찾기
        Partner partner = getPartnerByEmail(email);

        // 그 이메일을 가지고 있는(partner) 파트너가 없다면 오류 메세지 던지기
        if (partner == null) {
            throw new CustomException(ErrorMsg.PARTNER_NOT_FOUND);
        }

        // 모든 의뢰 조회
        List<Commission> commissions = commissionRepository.findAll();

        // 찾은 청소 의뢰 객체들을 DTO 리스트로 변환
        List<CommissionListResponseDto> commissionList = new ArrayList<>();

        for (Commission commission : commissions) {
            commissionList.add(new CommissionListResponseDto(
                    commission.getId(),
                    commission.getMembers().getNick(),
                    commission.getImage(),
                    commission.getSize(),
                    commission.getHouseType(),
                    commission.getCleanType(),
                    commission.getAddress().getAddress(),
                    commission.getDesiredDate(),
                    commission.getSignificant()
            ));
        }
        return commissionList;
    }


    //이메일로 파트너 찾는 메서드
    private Partner getPartnerByEmail(String email){
        return partnerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }
}
