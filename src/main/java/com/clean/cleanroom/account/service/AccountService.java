package com.clean.cleanroom.account.service;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.account.repository.AccountRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    // 계좌 생성 메서드
    public Account createAccount(AccountRequestDto accountRequestDto, String token) {
        String email = jwtUtil.extractEmail(token);
        Optional<Members> userOptional = membersRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            Account account = new Account(email, accountRequestDto);
            return accountRepository.save(account);
        } else {
            throw new CustomException(ErrorMsg.MEMBER_NOT_FOUND);
        }
    }

    // 계좌 삭제 메서드
    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new CustomException(ErrorMsg.ACCOUNT_NOT_FOUND);
        }
        accountRepository.deleteById(accountId);
    }

    // 모든 계좌 조회 메서드
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // ID로 계좌 조회 메서드
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ACCOUNT_NOT_FOUND));
    }

    // 선택된 계좌 설정 메서드
    @Transactional
    public Members selectAccount(String token, Long accountId) {
        String email = jwtUtil.extractEmail(token);
        Members member = membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ACCOUNT_NOT_FOUND));
        member.SelectedAccount(account);
        return membersRepository.save(member);
    }
}
