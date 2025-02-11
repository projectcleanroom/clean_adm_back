package com.clean.cleanroom.commission.entity;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;

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
    @JsonIgnore
    private Members members;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "commission", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Estimate> estimates;

    @Column(nullable = false, length = 255)
    @Comment("이미지")
    private String image;

    @Column(nullable = false)
    @Comment("평수")
    private int size;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("주거 형태")
    private HouseType houseType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("청소 종류")
    private CleanType cleanType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("의뢰 상태")
    private StatusType status;

    @Column(nullable = false)
    @Comment("희망 날짜")
    private LocalDateTime desiredDate;

    @Column(nullable = true, length = 255)
    @Comment("특이사항")
    private String significant;

    public Commission(Long id) {
        this.id = id;
    }
}
