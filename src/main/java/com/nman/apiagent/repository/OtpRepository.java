package com.nman.apiagent.repository;

import com.nman.apiagent.entity.Otp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository
		extends JpaRepository<Otp, Long> {

	Optional<Otp> findByOtpReferenceId(String otpReferenceId);

	Optional<Otp> findTopByAccountIdAndPurposeOrderByCreatedAtDesc(String accountId, String purpose);
}
