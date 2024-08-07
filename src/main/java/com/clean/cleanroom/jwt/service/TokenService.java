package com.clean.cleanroom.jwt.service;

import com.clean.cleanroom.enums.TokenType;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // 새로운 리프레시 토큰을 저장합니다.
    public void saveToken(Partner patrner, String refreshToken) {
        revokeAllUserTokens(patrner); // 무효화 먼저 실행
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .email(patrner.getEmail())
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    // 주어진 사용자의 모든 유효한 리프레시 토큰을 무효화합니다.
    public void revokeAllUserTokens(Partner patrner) {
        List<RefreshToken> validTokens = refreshTokenRepository.findAllValidTokenByEmail(patrner.getEmail());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.expire();
                t.revoke();
            });
            refreshTokenRepository.saveAll(validTokens);
        }
    }
}
