package com.clean.cleanroom.business.entity;

import com.clean.cleanroom.business.dto.BusinessInfoRequestDto;
import com.clean.cleanroom.business.dto.BusinessInfoUpdateRequestDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class BusinessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long businessNumber; //사업자 등록 번호

    @Column(nullable = false)
    private LocalDate openingDate; //개업 연월일

    @Column(nullable = false, length = 25)
    private String corporationName; //법인명

    @Column(nullable = false)
    private Long corporationNumber; //법인등록번호

    @Column(nullable = false, length = 255)
    private String location; //사업장 소재지

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;


    public BusinessInfo(BusinessInfoRequestDto request, Partner partner, Members members) {
        this.businessNumber = request.getBusinessNumber();
        this.openingDate = request.getOpeningDate();
        this.corporationName = request.getCorporationName();
        this.corporationNumber = request.getCorporationNumber();
        this.location = request.getLocation();
        this.partner = partner;
        this.members = members;
    }

    public void update(BusinessInfoUpdateRequestDto request, Partner partner) {
        this.businessNumber = request.getBusinessNumber();
        this.openingDate = request.getOpeningDate();
        this.corporationName = request.getCorporationName();
        this.corporationNumber = request.getCorporationNumber();
        this.location = request.getLocation();
        this.partner = partner;
    }
}
