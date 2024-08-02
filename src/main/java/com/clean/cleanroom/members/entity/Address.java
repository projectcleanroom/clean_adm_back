package com.clean.cleanroom.members.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members members;

    @Column(nullable = false, length = 30)
    private String address;


    //테스트 전용 생성자
    public Address(Long id, Members members, String address) {
        this.id = id;
        this.members = members;
        this.address = address;
    }
}
