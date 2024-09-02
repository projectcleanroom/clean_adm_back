package com.clean.cleanroom.partner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    private boolean isVerified;  // 인증 완료 여부를 나타내는 플래그

    public VerificationCode(String email, String verificationCode, LocalDateTime expirationTime) {
        this.email = email;
        this.code = verificationCode;
        this.expirationTime = expirationTime;
        this.isVerified = false;  // 기본적으로 인증되지 않은 상태로 설정
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationTime);
    }

    // 인증 코드와 만료 시간을 업데이트하는 메서드
    public void updateCodeAndExpiration(String newCode, LocalDateTime newExpirationTime) {
        this.code = newCode;
        this.expirationTime = newExpirationTime;
        this.isVerified = false;  // 새로운 코드가 생성되면 다시 인증되지 않은 상태로 변경
    }

    // 인증 완료 상태로 변경하는 메서드
    public void markAsVerified() {
        this.isVerified = true;
    }
}