package com.clean.cleanroom.business.entity;


import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사업자 정보 식별값

    @Column(nullable = false)
    private String businessNumber; // 사업자등록번호

    @Column(nullable = false)
    private LocalDate startDate; // 개업일

    @Column
    private String additionalRepresentativeName;

    @Column(nullable = false)
    private String representativeName; // 대표자 설명

    @Column(nullable = false)
    private String companyName; // 업체명

    @Column
    private String corporationNumber;

    @Column
    private String businessSector; // 사업 부문

    @Column
    private String businessType; // 업종

    // 사업자 등록번호 진위 여부 확인 결과
    @Column(nullable = false)
    private boolean isValid; // true - 정상, false - 오류

    @Column
    private LocalDate validationDate; // 검증된 날짜

    @ManyToOne
    @JoinColumn(name = "mambers_id")
    private Members members; // 연관된 파트너
}
