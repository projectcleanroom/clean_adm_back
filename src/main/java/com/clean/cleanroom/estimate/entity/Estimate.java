package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.EstimateCreateRequestDto;
import com.clean.cleanroom.estimate.dto.EstimatePatchRequestDto;
import com.clean.cleanroom.estimate.dto.EstimateUpdateRequestDto;
import com.clean.cleanroom.partner.entity.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    @JsonIgnore
    private Commission commission;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(nullable = false)
    @Comment("확정 가격")
    private int price;

    @Column(nullable = false)
    @Comment("임시 가격")
    private int tmpPrice;

    @Column(nullable = true)
    @Comment("확정 일자")
    private LocalDateTime fixedDate;

    @Column(nullable = true, length = 1000)
    @Comment("특이사항")
    private String statement;

    @Comment("승인 상태")
    private boolean approved = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("견적 상태")
    private StatusType status;




    public Estimate(EstimateCreateRequestDto estimateCreateRequestDto,
                    Long commissionId,
                    Partner partner) {
        this.commission = new Commission(commissionId);
        this.partner = partner;
        this.tmpPrice = estimateCreateRequestDto.getTmpPrice();
        this.statement = estimateCreateRequestDto.getStatement();
        this.fixedDate = LocalDateTime.parse(estimateCreateRequestDto.getFixedDate());
        this.approved = false;
        this.status = StatusType.CHECK;
    }

    public void updateEstimate(EstimateUpdateRequestDto estimateUpdateRequestDto,
                               Commission commission,
                               Partner partner) {
        this.commission = commission;
        this.partner = partner;
        this.price = estimateUpdateRequestDto.getPrice();
        this.fixedDate = estimateUpdateRequestDto.getFixedDate();
        this.statement = estimateUpdateRequestDto.getStatement();
        this.approved = false;
    }

    public void patchEstimate(EstimatePatchRequestDto estimatePatchRequestDto, Commission commission, Partner partner) {
        this.commission = commission;
        this.partner = partner;
        this.tmpPrice = estimatePatchRequestDto.getTmpPrice();
        this.status = estimatePatchRequestDto.getStatus();
    }
}
