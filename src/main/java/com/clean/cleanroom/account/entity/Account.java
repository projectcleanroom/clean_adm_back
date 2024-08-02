package com.clean.cleanroom.account.entity;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "account_number", nullable = false, length = 25)
    private String accountNumber;

    @Column(name = "bank", nullable = false, length = 25)
    private String bank;

    public Account(String email, AccountRequestDto accountRequestDto) {
        this.email = email;
        this.accountNumber = accountRequestDto.getAccountNumber();
        this.bank = accountRequestDto.getBank();
    }
}
