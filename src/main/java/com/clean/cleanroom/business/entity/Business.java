package com.clean.cleanroom.business.entity;


import com.clean.cleanroom.members.entity.Members;
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
    private Long id;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String representativeName;

    @Column
    private String additionalRepresentativeName;

    @Column(nullable = false)
    private String companyName;

    @Column
    private String corporationNumber;

    @Column
    private String businessSector;

    @Column
    private String businessType;

    @Column
    private String businessAddress;

    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;
}
