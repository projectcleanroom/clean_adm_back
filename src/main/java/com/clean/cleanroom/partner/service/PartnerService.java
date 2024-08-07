package com.clean.cleanroom.partner.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final JwtUtil jwtUtil;

    public PartnerService(PartnerRepository partnerRepository, JwtUtil jwtUtil) {
        this.partnerRepository = partnerRepository;
        this.jwtUtil = jwtUtil;
    }

    //파트너 회원가입
    public PartnerSignupResponseDto signup(@Valid PartnerRequestDto requestDto) {
        Partner partner = new Partner(requestDto);
        partner.setPassword(requestDto.getPassword());
        partnerRepository.save(partner);
            return new PartnerSignupResponseDto(partner);
        }

    //파트너 회원 정보 수정
    public PartnerProfileResponseDto profile(String token, PartnerRequestDto requestDto) {
       String email = jwtUtil.extractEmail(token);
       Partner partner = partnerRepository.findByEmail(email).orElseThrow(
               () -> new CustomException(ErrorMsg.INVALID_ID)
       );
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