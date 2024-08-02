package com.clean.cleanroom.account.controller;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import com.clean.cleanroom.account.dto.MessageResponseDto;
import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountInfoService;
    @Autowired
    private AccountService accountService;

    // 계좌 생성 메서드
    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> createAccount(@RequestBody AccountRequestDto accountRequestDto, @RequestHeader("Authorization") String token) {
        accountInfoService.createAccount(accountRequestDto, token);
        return ResponseEntity.ok(new MessageResponseDto("등록되었습니다."));
    }

    // 계좌 삭제 메서드
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteAccount(@RequestParam Long accountId) {
        accountInfoService.deleteAccount(accountId);
        return ResponseEntity.ok(new MessageResponseDto("삭제되었습니다."));
    }

    // 모든 계좌 조회 메서드
    @GetMapping("/list")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountInfoService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // 특정 계좌 조회 메서드
    @GetMapping()
    public ResponseEntity<Account> getAccountById(@RequestParam Long accountId) {
        Account account = accountInfoService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    // 선택된 계좌 설정 메서드
    @PostMapping("/select")
    public ResponseEntity<MessageResponseDto> selectAccount(@RequestParam Long accountId, @RequestHeader("Authorization") String token) {
        accountService.selectAccount(token, accountId);
        return ResponseEntity.ok(new MessageResponseDto("계좌가 선택되었습니다."));
    }
}
