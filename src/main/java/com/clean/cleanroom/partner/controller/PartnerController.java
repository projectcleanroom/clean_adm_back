package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.estimate.dto.EstimateDeleteResponseDto;
import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.EmailSenderService;
import com.clean.cleanroom.partner.service.PartnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/partner")
@Tag(name = "파트너")
public class PartnerController {

    private final PartnerService partnerService;
    private final EmailSenderService emailSenderService;

    public PartnerController(PartnerService partnerService, EmailSenderService emailSenderService) {
        this.partnerService = partnerService;
        this.emailSenderService = emailSenderService;
    }

    // 회원가입 전 이메일 인증 요청
    @PostMapping("/request-email-verification")
    public ResponseEntity<String> requestEmailVerification(@RequestBody @Valid EmailVerificationRequestDto requestDto) {
        String verificationCode = partnerService.generateEmailVerificationCode(requestDto.getEmail());
        emailSenderService.sendVerificationEmail(requestDto.getEmail(), verificationCode);
        return new ResponseEntity<>("인증 코드가 전송되었습니다.", HttpStatus.OK);
    }

    // 이메일 인증 완료
    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifcationCodeRequestDto request) {
        partnerService.verifyEmail(request.getEmail(), request.getCode());
        return new ResponseEntity<>("이메일이 성공적으로 인증되었습니다.", HttpStatus.OK);
    }

    @PostMapping ("/signup")
    public ResponseEntity<PartnerSignupResponseDto> signup (@RequestBody @Valid PartnerRequestDto requestDto) {
        PartnerSignupResponseDto partnerSignupResponseDto = partnerService.signup(requestDto);
        return new ResponseEntity<>(partnerSignupResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/profile")
    public ResponseEntity<PartnerProfileResponseDto> profile (@RequestHeader("Authorization") String token, @RequestBody PartnerUpdateProfileRequestDto requestDto) {
        PartnerProfileResponseDto partnerProfileResponseDto = partnerService.profile(token, requestDto);
        return new ResponseEntity<>(partnerProfileResponseDto, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<PartnerGetProfileResponseDto> getProfile(@RequestHeader("Authorization") String token) {
        PartnerGetProfileResponseDto partnerGetProfileResponseDto = partnerService.getProfile(token);
        return new ResponseEntity<>(partnerGetProfileResponseDto, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<PartnerUploadResponseDto> imgUpload(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) {
        PartnerUploadResponseDto partnerUploadResponseDto = partnerService.imgPtUpload(token, file);
        return new ResponseEntity<>(partnerUploadResponseDto, HttpStatus.OK);
    }

    @GetMapping("/upload")
    public ResponseEntity<byte[]> imgGet(@RequestHeader ("Authorization") String token, @RequestParam String file) {
        PartnerUploadGetResponseDto partnerUploadResponseDto = partnerService.imgPtGet(token, file);
        // 파일의 MIME 타입 설정
        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get("/uploads/" + file));
            if (contentType == null) {
                contentType = "application/octet-stream";  // MIME 타입을 추정할 수 없을 때 기본값
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";  // 오류 발생 시 기본값
        }
        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, contentType);
        return new ResponseEntity<>(partnerUploadResponseDto.getFileData(), headers, HttpStatus.OK);
    }

    // 회원 탈퇴
    @PatchMapping("/delete")
    public ResponseEntity<PartnerDeleteResponseDto> deletePartner(@RequestHeader("Authorization") String token) {
        PartnerDeleteResponseDto responseDto = partnerService.deletePartner (token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
