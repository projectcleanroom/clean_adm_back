package com.clean.cleanroom.partner.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final JwtUtil jwtUtil;

    public PartnerService(PartnerRepository partnerRepository, JwtUtil jwtUtil) {
        this.partnerRepository = partnerRepository;
        this.jwtUtil = jwtUtil;
    }

    //파트너 회원가입
    @Transactional
    public PartnerSignupResponseDto signup(@Valid PartnerRequestDto requestDto) {
        Partner partner = new Partner(requestDto);
        // email 유무
        if (!partner.getEmail().equals(requestDto.getEmail()) &&
                partnerRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorMsg.INVALID_ID);
        }
        // phoneNumber 유무
        if (!partner.getPhoneNumber().equals(requestDto.getPhoneNumber()) &&
                partnerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // managerName 유무
        if (!partner.getManagerName().equals(requestDto.getManagerName()) &&
                partnerRepository.existsByManagerName(requestDto.getManagerName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_MANAGERNAME);
        }
        // companyName 유무
        if (!partner.getCompanyName().equals(requestDto.getCompanyName()) &&
                partnerRepository.existsByCompanyName(requestDto.getCompanyName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_COMPANYNAME);
        }
        partner.setPassword(requestDto.getPassword());
        partnerRepository.save(partner);
        return new PartnerSignupResponseDto(partner);
    }

    //파트너 회원 정보 수정
    @Transactional
    public PartnerProfileResponseDto profile(String token, PartnerRequestDto requestDto) {
        String email = jwtUtil.extractEmail(token);
        // email 유무
        Partner partner = partnerRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID));
        // phoneNumber 유무
        if (!partner.getPhoneNumber().equals(requestDto.getPhoneNumber()) &&
                partnerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // managerName 유무
        if (!partner.getManagerName().equals(requestDto.getManagerName()) &&
                partnerRepository.existsByManagerName(requestDto.getManagerName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_MANAGERNAME);
        }
        // companyName 유무
        if (!partner.getCompanyName().equals(requestDto.getCompanyName()) &&
                partnerRepository.existsByCompanyName(requestDto.getCompanyName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_COMPANYNAME);
        }
        // 비밀번호 일치 확인
        if (!partner.checkPassword(requestDto.getPassword())) {
            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
        }
        partner.partner(requestDto);
        partnerRepository.save(partner);
        return new PartnerProfileResponseDto(partner);
    }

    public PartnerGetProfileResponseDto getProfile(String token) {
        String email = jwtUtil.extractEmail(token);
        Partner partner = partnerRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID)
        );
        return new PartnerGetProfileResponseDto(partner);
    }
}