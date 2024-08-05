package com.clean.cleanroom.partner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phoneNumber;


    public Partner (Long id, String email ,String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
