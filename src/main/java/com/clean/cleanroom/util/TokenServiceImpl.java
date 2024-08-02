package com.clean.cleanroom.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;

    public TokenServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //요청헤더에서 토큰안의 이메일을 추출하는 메서드
    @Override
    public String getEmailFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return jwtUtil.getEmailFromToken(token);
    }
}
