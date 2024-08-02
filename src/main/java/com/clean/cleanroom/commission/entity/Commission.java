package com.clean.cleanroom.commission.entity;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionUpdateRequestDto;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false, length = 255)
    private String image;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseType houseType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CleanType cleanType;

    @Column(nullable = false)
    private LocalDateTime desiredDate;

    @Column(nullable = true, length = 255)
    private String significant;


    public Commission(Members members, Address address,CommissionCreateRequestDto requestDto) {
        this.members = members;
        this.address = address;
        this.image = requestDto.getImage();
        this.size = requestDto.getSize();
        this.houseType = requestDto.getHouseType();
        this.cleanType = requestDto.getCleanType();
        this.desiredDate = requestDto.getDesiredDate();
        this.significant = requestDto.getSignificant();
    }

    public void update(CommissionUpdateRequestDto requestDto, Address address) {
        this.image = requestDto.getImage();
        this.address = address;
        this.size = requestDto.getSize();
        this.houseType = requestDto.getHouseType();
        this.cleanType = requestDto.getCleanType();
        this.desiredDate = requestDto.getDesiredDate();
        this.significant = requestDto.getSignificant();
    }
}
