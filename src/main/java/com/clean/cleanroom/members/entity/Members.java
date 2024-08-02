package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.business.entity.BusinessInfo;
import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.util.PasswordUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String nick;

    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "members")
    private List<Address> address;

    @ManyToOne
    private Account selectedAccount;

    @OneToMany(mappedBy = "members")
    private List<BusinessInfo> businessInfo;

    public Members(MembersRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.nick = requestDto.getNick();
        this.phoneNumber = requestDto.getPhoneNumber();
    }

    public void members(MembersRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.nick = requestDto.getNick();
        this.phoneNumber = requestDto.getPhoneNumber();
    }

    public boolean checkPassword(String password) {
        return PasswordUtil.matches(password, this.password);
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.encodePassword(password);
    }

    public void SelectedAccount(Account account) {
        this.selectedAccount = account;
    }
}
