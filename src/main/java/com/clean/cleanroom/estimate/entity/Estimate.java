package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.estimate.dto.EstimateCreateRequestDto;
import com.clean.cleanroom.estimate.dto.EstimateUpdateRequestDto;
import com.clean.cleanroom.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "commission_id")
//    private Commission commissionId;

    private Long commissionId; //테스트용

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private LocalDateTime fixedDate;

    @Column(nullable = true, length = 1000)
    private String statement;



    public Estimate(EstimateCreateRequestDto estimateCreateRequestDto, Partner partner) {
        this.commissionId = estimateCreateRequestDto.getCommissionId();
        this.partner = partner;
        this.price = estimateCreateRequestDto.getPrice();
        this.statement = estimateCreateRequestDto.getStatement();
        this.fixedDate = LocalDateTime.parse(estimateCreateRequestDto.getFixedDate());
    }

    public void updateEstimate(EstimateUpdateRequestDto estimateUpdateRequestDto, Partner partner) {
        this.commissionId = estimateUpdateRequestDto.getCommissionId();
        this.partner = partner;
        this.price = estimateUpdateRequestDto.getPrice();
        this.fixedDate = estimateUpdateRequestDto.getFixedDate();
        this.statement = estimateUpdateRequestDto.getStatement();
    }
}
