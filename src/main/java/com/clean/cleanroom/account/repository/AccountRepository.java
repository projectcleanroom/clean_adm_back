package com.clean.cleanroom.account.repository;

import com.clean.cleanroom.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
