package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateListResponseDto {


    @Schema(description = "견적 식별값")
    private long id;

    @Schema(description = "의뢰 식별값")
    private Long commissionId;

    @Schema(description = "파트너 식별값")
    private Long partnerId;

    @Schema(description = "임시 가격")
    private int tmpPrice;

    @Schema(description = "확정 일자", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate;

    @Schema(description = "코멘트")
    private String statement;

    @Schema(description = "회원 닉네임")
    private String nick;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "이미지")
    private String image;

    @Schema(description = "주거 형태")
    private HouseType houseType;

    @Schema(description = "청소 타입")
    private CleanType cleanType;

    @Schema(description = "희망 일자", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate; // 희망날짜

    @Schema(description = "견적 상태")
    private StatusType status;

    @Schema(description = "의뢰 상태")
    private StatusType commissionStatus; 




    public EstimateListResponseDto(Estimate estimate,
                                     Members members,
                                     Address address,
                                     Commission commission) {
        this.id = estimate.getId();
        this.commissionId = estimate.getCommission().getId();
        this.partnerId = estimate.getPartner().getId();
        this.tmpPrice = estimate.getTmpPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.nick = members.getNick();
        this.address = address.getAddress();
        this.image = commission.getImage();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.desiredDate = commission.getDesiredDate();
        this.status = estimate.getStatus();
        this.commissionStatus = commission.getStatus(); 
    }
}
