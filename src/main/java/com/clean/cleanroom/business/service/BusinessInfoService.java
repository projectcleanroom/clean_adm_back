package com.clean.cleanroom.business.service;

import com.clean.cleanroom.business.dto.BusinessInfoRequestDto;
import com.clean.cleanroom.business.dto.BusinessInfoResponseDto;
import com.clean.cleanroom.business.dto.BusinessInfoUpdateRequestDto;
import com.clean.cleanroom.business.dto.BusinessInfoUpdateResponseDto;
import com.clean.cleanroom.business.entity.BusinessInfo;
import com.clean.cleanroom.business.repository.BusinessInfoRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessInfoService {

    private final BusinessInfoRepository businessInfoRepository;
    private final PartnerRepository partnerRepository;
    private final MembersRepository membersRepository;


    public BusinessInfoService(BusinessInfoRepository businessInfoRepository, PartnerRepository partnerRepository, MembersRepository membersRepository) {
        this.businessInfoRepository = businessInfoRepository;
        this.partnerRepository = partnerRepository;
        this.membersRepository = membersRepository;
    }

    //사업자 등록
    public BusinessInfoResponseDto createBusinessInfo(String email, BusinessInfoRequestDto request) {

        //사업자 등록할 청소업체 찾기
        Partner partner = partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PARTNER_NOT_FOUND));

        //사업자 등록하는 유저 확인
        Members members = membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));

        //사업자 등록 객체 생성
        BusinessInfo businessInfo = new BusinessInfo(request, partner, members);

        //사업자 등록
        BusinessInfo savedBusinessInfo = businessInfoRepository.save(businessInfo);

        //DTO 반환
        BusinessInfoResponseDto responseDto = new BusinessInfoResponseDto(savedBusinessInfo);
        return responseDto;
    }

    //사업자 등록 수정
    @Transactional
    public BusinessInfoUpdateResponseDto updateBusinessInfo(String email, Long BusinessInfoId, BusinessInfoUpdateRequestDto request) {


        //유저가 등록한 사업자 등록정보 찾기
        BusinessInfo businessInfo = businessInfoRepository.findByIdAndMembersEmail(BusinessInfoId, email)
                .orElseThrow(() -> new CustomException(ErrorMsg.BUSINESS_INFO_NOT_FOUND));

        //청소업체 찾기
        Partner partner = partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PARTNER_NOT_FOUND));

        //사업자 등록 업데이트
        businessInfo.update(request, partner);

        //DTO 반환
        BusinessInfoUpdateResponseDto response =new BusinessInfoUpdateResponseDto(businessInfo);
        return response;
    }

}
