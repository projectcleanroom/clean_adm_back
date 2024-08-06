package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembersLoginService {

    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    // 로그인 로직
    public ResponseEntity<MembersLoginResponseDto> login(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회. 없으면 예외를 던짐
        Members member = membersRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorMsg.INVALID_ID));

        // 비밀번호가 일치하는지 확인. 일치하지 않으면 예외를 던짐
        if (!member.checkPassword(requestDto.getPassword())) {
            throw new CustomException(ErrorMsg.INVALID_PASSWORD);
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateAccessToken(member.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(member.getEmail());

        // 토큰 저장
        tokenService.saveToken(member, refreshToken);

        // HTTP 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Refresh-Token", "Bearer " + refreshToken);

        // 응답 DTO 생성
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(member);

        // 응답 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(responseDto);
    }

    // 로그아웃 로직
    public ResponseEntity<MembersLogoutResponseDto> logout(String accessToken, String refreshToken) {
        // Access Token 검증 및 무효화 처리
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            String actualAccessToken = accessToken.substring(7);
            if (jwtUtil.validateToken(actualAccessToken)) {
                jwtUtil.revokeToken(actualAccessToken);
            }
        }

        // Refresh Token 검증 및 무효화 처리
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String actualRefreshToken = refreshToken.substring(7);
            if (jwtUtil.validateToken(actualRefreshToken)) {
                jwtUtil.revokeToken(actualRefreshToken);
            }
        }

        // 로그아웃 성공 응답 반환
        MembersLogoutResponseDto response = new MembersLogoutResponseDto("로그아웃 되었습니다.");
        return ResponseEntity.ok(response);
    }
}
