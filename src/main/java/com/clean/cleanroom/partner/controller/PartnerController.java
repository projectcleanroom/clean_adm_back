package com.clean.cleanroom.partner.controller;

import com.clean.cleanroom.partner.dto.*;
import com.clean.cleanroom.partner.service.PartnerService;
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
public class PartnerController {

    private final PartnerService partnerService;
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
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
}
