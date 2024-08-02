package com.clean.cleanroom.config;

import com.clean.cleanroom.filter.JwtAuthenticationFilter;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        // 필터 등록 빈 생성
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        // JWT 인증 필터 설정
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil, tokenService)); // 두 인자 전달

        // 필터가 적용될 URL 패턴 설정
        registrationBean.addUrlPatterns("/api/*");

        // 필터 등록 빈 반환
        return registrationBean;
    }
}