package com.clean.cleanroom.partner.service;

import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    //파트너 회원가입
    public PartnerSignupResponseDto signup(PartnerSignupRequestDto partnerSignupRequestDto) {
        String messege = "";
        PartnerSignupResponseDto partnerSignupResponseDto = new PartnerSignupResponseDto(messege);
        return partnerSignupResponseDto;
    }

    //파트너 로그인
    public PartnerLoginResponseDto login (PartnerLoginRequestDto partnerLoginRequestDto) {
        String messege = "";
        PartnerLoginResponseDto partnerLoginResponseDto = new PartnerLoginResponseDto(messege);
        return partnerLoginResponseDto;
    }

    //파트너 회원 정보 수정
    public PartnerUpdateResponseDto update (Long id, PartnerUpdateRequestDto partnerUpdateRequestDto) {
        String messege = "";
        PartnerUpdateResponseDto partnerUpdateResponseDto = new PartnerUpdateResponseDto();
        return partnerUpdateResponseDto;
    }

    public PartnerLogoutResponseDto logout(String accessToken, String refreshToken) {
        String messege = "";
        PartnerLogoutResponseDto partnerLogoutResponseDto = new PartnerLogoutResponseDto(messege);
        return partnerLogoutResponseDto;
    }
}