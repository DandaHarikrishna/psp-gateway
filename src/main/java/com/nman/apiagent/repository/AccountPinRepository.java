package com.nman.apiagent.repository;

import com.nman.apiagent.entity.AccountPin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountPinRepository
        extends JpaRepository<AccountPin, Long> {

    Optional<AccountPin> findByAccountId(
            String accountId
    );

    boolean existsByAccountId(
            String accountId
    );
}
