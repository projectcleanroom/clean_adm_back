package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.dto.TokenResponseDto;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    // JWT 서명을 위한 키
    private Key key;
    private final MembersRepository membersRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 객체 초기화 후 키 생성
    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // 액세스 토큰 만료 시간 (1시간)
    private final int accessTokenExpiration = 3600;
    // 리프레시 토큰 만료 시간 (2주)
    private final int refreshTokenExpiration = 1209600;

    // 이메일을 사용해 액세스 토큰을 생성
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 이메일을 사용해 리프레시 토큰을 생성
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            // 서명 예외 처리
            log.error("Exception occurred while parsing token: {}", e.getMessage());
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        }
    }

    public String extractEmail(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return getEmailFromToken(token);
    }


    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 개별 토큰을 무효화하는 메서드
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElse(null);
        if (refreshToken != null) {
            refreshToken.expire();
            refreshToken.revoke();
            refreshTokenRepository.save(refreshToken);
        }
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Authorization 헤더에서 리프레시 토큰 추출
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        // Authorization 헤더가 존재하지 않거나 Bearer 토큰이 아니면 401 Unauthorized 응답
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Bearer 토큰에서 실제 리프레시 토큰 값 추출
        refreshToken = authHeader.substring(7);
        userEmail = getEmailFromToken(refreshToken);

        // 이메일이 존재하고 리프레시 토큰이 유효한 경우
        if (userEmail != null) {
            Members member = this.membersRepository.findByEmail(userEmail).orElse(null);

            // 사용자가 존재하고 리프레시 토큰이 유효한 경우 새로운 액세스 토큰 발급
            if (member != null && validateToken(refreshToken)) {
                String accessToken = generateAccessToken(member.getEmail());
                TokenResponseDto authResponse = new TokenResponseDto(accessToken, refreshToken);

                // 응답 헤더에 새로운 액세스 토큰 추가
                response.setHeader("Authorization", "Bearer " + accessToken);

                // 응답 본문에 액세스 토큰과 리프레시 토큰을 JSON 형식으로 작성
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                // 사용자가 없거나 리프레시 토큰이 유효하지 않은 경우 401 Unauthorized 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            // 이메일이 존재하지 않는 경우 401 Unauthorized 응답
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
