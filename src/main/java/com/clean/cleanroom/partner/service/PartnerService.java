package com.clean.cleanroom.partner.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.partner.dto.PartnerUploadGetResponseDto;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.entity.VerificationCode;
import com.clean.cleanroom.partner.repository.PartnerRepository;
import com.clean.cleanroom.partner.repository.VerificationCodeRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final JwtUtil jwtUtil;

    public PartnerService(PartnerRepository partnerRepository, JwtUtil jwtUtil, VerificationCodeRepository verificationCodeRepository) {
        this.partnerRepository = partnerRepository;
        this.jwtUtil = jwtUtil;
        this.verificationCodeRepository = verificationCodeRepository;
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
        // 이메일 인증 여부 확인
        if (!isEmailVerified(requestDto.getEmail())) {
            throw new CustomException(ErrorMsg.EMAIL_NOT_VERIFIED);
        }

        Partner partner = new Partner(requestDto);
        partner.setPassword(requestDto.getPassword());
        partnerRepository.save(partner);
        return new PartnerSignupResponseDto(partner);
    }

    // 이메일 인증 코드를 생성하고 데이터베이스에 저장하거나 업데이트하는 메서드
    @Transactional
    public String generateEmailVerificationCode(String email) {
        // 6자리 랜덤 인증 코드 생성
        String verificationCode = generateVerificationCode();
        // 인증 코드의 만료 시간을 현재 시간으로부터 10분 후로 설정
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        // 이메일로 기존에 저장된 인증 코드가 있는지 확인
        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByEmail(email);
        if (optionalVerificationCode.isPresent()) {
            // 기존 코드가 있으면 코드와 만료 시간을 업데이트
            VerificationCode existingCode = optionalVerificationCode.get();
            existingCode.updateCodeAndExpiration(verificationCode, expirationTime);
            verificationCodeRepository.save(existingCode);
        } else {
            // 기존 코드가 없으면 새로운 인증 코드 생성
            VerificationCode newCode = new VerificationCode(email, verificationCode, expirationTime);
            verificationCodeRepository.save(newCode);
        }

        return verificationCode;
    }

    // 랜덤한 6자리 숫자 인증 코드를 생성하는 메서드
    private String generateVerificationCode() {
        // 0부터 999999 사이의 랜덤 숫자를 생성하여 6자리 문자열로 반환
        return String.format("%06d", new Random().nextInt(999999));
    }

    // 사용자가 입력한 이메일과 인증 코드를 검증하는 메서드
    @Transactional
    public void verifyEmail(String email, String code) {
        // 이메일에 해당하는 인증 코드 조회, 없으면 예외 발생
        VerificationCode storedCode = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE));

        // 인증 코드가 만료되었는지 확인
        if (storedCode.isExpired()) {
            throw new CustomException(ErrorMsg.EXPIRED_VERIFICATION_CODE);
        }

        // 사용자가 입력한 코드가 저장된 코드와 일치하는지 확인
        if (!storedCode.getCode().equals(code)) {
            throw new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE);
        }

        // 인증 완료 상태로 변경
        storedCode.markAsVerified();
        verificationCodeRepository.save(storedCode);
    }

    // 사용자의 이메일이 인증되었는지 확인하는 메서드
    public boolean isEmailVerified(String email) {
        // 이메일에 해당하는 인증 코드가 존재하고, 인증되었는지 여부를 반환
        return verificationCodeRepository.findByEmail(email)
                .map(VerificationCode::isVerified)
                .orElse(false);
    }

    //파트너 회원 정보 수정
    @Transactional
    public PartnerProfileResponseDto profile(String token, PartnerUpdateProfileRequestDto requestDto) {
        String email = jwtUtil.extractEmail(token);
        // email 유무
        Partner partner = partnerRepository.findActiveByEmail(email).orElseThrow(
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
        Partner partner = partnerRepository.findActiveByEmail(email).orElseThrow(
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


    // 회원 탈퇴
    public PartnerDeleteResponseDto deletePartner(String token) {

        // 토큰에서 파트너 찾기
        Partner partner = getPartnerFromToken(token);

        // 이미 탈퇴된 경우 예외 처리
        if (partner.isDeleted()) {
            throw new CustomException(ErrorMsg.PARTNER_ALREADY_DELETED);
        }

        // 파트너 소프트 딜리트 메서드 호출
        partner.softDelete();

        // 레포지토리에 저장
        partnerRepository.save(partner);

        return new PartnerDeleteResponseDto(partner);
    }


    // 토큰에서 이메일 추출
    private Partner getPartnerFromToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return getPartnerByEmail(email);
    }

    // 이메일로 파트너 찾기
    private Partner getPartnerByEmail (String email){
        return partnerRepository.findActiveByEmail(email)
                .orElseThrow(()->new CustomException(ErrorMsg.PARTNER_NOT_FOUND));
    }
}


