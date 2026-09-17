package com.nman.apiagent.repository;

import com.nman.apiagent.entity.LinkedBankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkedBankAccountRepository
		extends JpaRepository<LinkedBankAccount, Long> {

	Optional<LinkedBankAccount> findByAccountId(String accountId);

	boolean existsByAccountId(String accountId);

	Optional<LinkedBankAccount> findByMobileNumberAndIfsc(String mobileNumber, String ifsc);
}
