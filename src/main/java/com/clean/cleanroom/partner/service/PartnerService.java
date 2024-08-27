package com.clean.cleanroom.partner.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.dto.PartnerUploadGetResponseDto;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public PartnerSignupResponseDto signup(PartnerRequestDto requestDto) {
        // email 유무
        if (partnerRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorMsg.DUPLICATE_EMAIL);
        }
        // phoneNumber 유무
        if (partnerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // companyName 유무
        if (partnerRepository.existsByCompanyName(requestDto.getCompanyName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_COMPANYNAME);
        }
        Partner partner = new Partner(requestDto);
        partner.setPassword(requestDto.getPassword());
        partnerRepository.save(partner);
        return new PartnerSignupResponseDto(partner);
    }

    //파트너 회원 정보 수정
    @Transactional
    public PartnerProfileResponseDto profile(String token, PartnerUpdateProfileRequestDto requestDto) {
        String email = jwtUtil.extractEmail(token);
        // email 유무
        Partner partner = partnerRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID));
        // phoneNumber 유무
        if (!partner.getPhoneNumber().equals(requestDto.getPhoneNumber()) &&
                partnerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // companyName 유무
        if (!partner.getCompanyName().equals(requestDto.getCompanyName()) &&
                partnerRepository.existsByCompanyName(requestDto.getCompanyName())) {
            throw new CustomException(ErrorMsg.DUPLICATE_COMPANYNAME);
        }
        // 비밀번호 일치 확인
//        if (!partner.checkPassword(requestDto.getPassword())) {
//            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
//        }
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            partner.setPassword(requestDto.getPassword());
        }
        //        partner.partner(requestDto);
//        partner.setPassword(requestDto.getPassword());
        partner.updatePartner(requestDto);
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

    private static final Logger logger = LoggerFactory.getLogger(PartnerService.class);
    private static final String UPLOAD_DIR = "/uploads/";
    public PartnerUploadResponseDto imgPtUpload(String token, MultipartFile file) {
        String email = jwtUtil.extractEmail(token);
        try {
            // 이미지 파일만 허용
            if (!isImageFile(file)) {
                return new PartnerUploadResponseDto(null, "Only image files are allowed.");
            }

            // 파일 저장 로직
            saveFile(file);
            String filePath = UPLOAD_DIR + file.getOriginalFilename(); // 파일 경로
            logger.info("File successfully uploaded to: " + filePath);
            return new PartnerUploadResponseDto(file.getOriginalFilename(), "File uploaded successfully");
        } catch (IOException e) {
            logger.error("File upload failed due to IOException: ", e);
            return new PartnerUploadResponseDto(null, "File upload failed");
        }
    }

    private void saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();  // 디렉토리가 없으면 생성
        }
        File destinationFile = new File(UPLOAD_DIR + file.getOriginalFilename());
        file.transferTo(destinationFile);  // 파일 저장
        logger.info("File transferred to: " + destinationFile.getAbsolutePath());
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif"));
    }

    public PartnerUploadGetResponseDto imgPtGet(String token, String file) {
        String email = jwtUtil.extractEmail(token);
        Path filePath = Paths.get(UPLOAD_DIR + file);
        try {
            // 파일이 존재하는지 확인
            if (!Files.exists(filePath)) {
                logger.error("File not found: " + file);
                return new PartnerUploadGetResponseDto(file, "File not found", null);
            }

            // 파일을 읽어 바이트 배열로 변환
            byte[] fileData = Files.readAllBytes(filePath);
            logger.info("File successfully retrieved: " + file);

            // 성공 메시지와 함께 DTO 반환
            return new PartnerUploadGetResponseDto(file, "File retrieved successfully", fileData);

        } catch (IOException e) {
            // 파일 읽기 실패 시 처리
            logger.error("Failed to read file: " + file, e);
            return new PartnerUploadGetResponseDto(file, "Failed to retrieve file", null);
        }

    }
}