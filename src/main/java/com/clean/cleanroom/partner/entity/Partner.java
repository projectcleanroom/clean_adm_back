package com.clean.cleanroom.partner.entity;

import com.clean.cleanroom.enums.PartnerType;
import com.clean.cleanroom.partner.dto.PartnerRequestDto;
import com.clean.cleanroom.partner.dto.PartnerUpdateProfileRequestDto;
import com.clean.cleanroom.util.PasswordUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 15)
    private String managerName;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 15)
    private String businessType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PartnerType partnerType;

    @Comment("탈퇴 여부")
    private boolean isDeleted = false;

    @Comment("탈퇴 날짜")
    private LocalDateTime deletedAt;



    public Partner (PartnerRequestDto requestDto) {

        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.managerName = requestDto.getManagerName();
        this.companyName = requestDto.getCompanyName();
        this.businessType = requestDto.getBusinessType();
        this.partnerType = requestDto.getPartnerType();
    }


    public void partner (PartnerRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.managerName = requestDto.getManagerName();
        this.companyName = requestDto.getCompanyName();
        this.businessType = requestDto.getBusinessType();
        this.partnerType = requestDto.getPartnerType();
    }

    public boolean checkPassword(String password) {
        return PasswordUtil.matches(password, this.password);
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.encodePassword(password);
    }

    public void updatePartner(PartnerUpdateProfileRequestDto requestDto) {
        if (requestDto.getPhoneNumber() != null) {
            this.phoneNumber = requestDto.getPhoneNumber();
        }
        if (requestDto.getManagerName() != null) {
            this.managerName = requestDto.getManagerName();
        }
        if (requestDto.getCompanyName() != null) {
            this.companyName = requestDto.getCompanyName();
        }
        if (requestDto.getBusinessType() != null) {
            this.businessType = requestDto.getBusinessType();
        }
        if (requestDto.getPartnerType() != null) {
            this.partnerType = requestDto.getPartnerType();
        }
    }


    // 회원 탈퇴 처리 메서드
    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
