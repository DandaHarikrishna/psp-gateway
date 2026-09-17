package com.nman.apiagent.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nman.apiagent.dto.SendOtpRequest;
import com.nman.apiagent.dto.SendOtpResponse;
import com.nman.apiagent.entity.Otp;
import com.nman.apiagent.repository.LinkedBankAccountRepository;
import com.nman.apiagent.repository.OtpRepository;

@Service
public class OtpService {

	private final SecureRandom secureRandom = new SecureRandom();

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final LinkedBankAccountRepository bankAccountRepository;

	private final OtpRepository otpRepository;

	public OtpService(LinkedBankAccountRepository bankAccountRepository, OtpRepository otpRepository) {

		this.bankAccountRepository = bankAccountRepository;

		this.otpRepository = otpRepository;
	}

	@Transactional
	public SendOtpResponse sendOtp(String accountId, SendOtpRequest request) {

		// 1. Validate account

		if (!bankAccountRepository.existsByAccountId(accountId)) {

			throw new IllegalArgumentException("Bank account not found: " + accountId);
		}

		// 2. Generate OTP

		String otp = generateOtp();

		// 3. Generate reference

		String otpReferenceId = "OTP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		// 4. Hash OTP

		String otpHash = passwordEncoder.encode(otp);

		// 5. Create entity

		Otp otpEntity = new Otp();

		otpEntity.setAccountId(accountId);
		otpEntity.setOtpReferenceId(otpReferenceId);
		otpEntity.setOtpHash(otpHash);

		otpEntity.setPurpose(request.getPurpose());

		otpEntity.setExpiresAt(LocalDateTime.now().plusMinutes(5));

		otpEntity.setAttempts(0);
		otpEntity.setVerified(false);

		// 6. Save

		otpRepository.save(otpEntity);

		/*
		 * Do NOT return OTP in production.
		 *
		 * Your SMS provider should receive the generated OTP here.
		 *
		 * smsProvider.send( request.getMobileNumber(), otp );
		 */

		String maskedMobile = maskMobileNumber(request.getMobileNumber());

		SendOtpResponse.OtpData data = new SendOtpResponse.OtpData(otpReferenceId, maskedMobile, 300);

		return new SendOtpResponse(true, "OTP sent successfully", request.getReferenceId(), data);
	}

	private String generateOtp() {

		int otp = 100000 + secureRandom.nextInt(900000);

		return String.valueOf(otp);
	}

	private String maskMobileNumber(String mobileNumber) {

		if (mobileNumber == null || mobileNumber.length() < 4) {

			return "******";
		}

		return "******" + mobileNumber.substring(mobileNumber.length() - 4);
	}
	public boolean verifyOtp(
	        String accountId,
	        String otpReferenceId,
	        String otp) {

	    Otp otpEntity =
	            otpRepository
	                    .findByOtpReferenceId(
	                            otpReferenceId)
	                    .orElseThrow(() ->
	                            new IllegalArgumentException(
	                                    "OTP not found"));

	    // Verify account

	    if (!otpEntity.getAccountId()
	            .equals(accountId)) {

	        throw new IllegalArgumentException(
	                "OTP does not belong to this account");
	    }

	    // Already verified

	    if (otpEntity.isVerified()) {

	        throw new IllegalArgumentException(
	                "OTP already used");
	    }

	    // Expired

	    if (LocalDateTime.now()
	            .isAfter(otpEntity.getExpiresAt())) {

	        throw new IllegalArgumentException(
	                "OTP expired");
	    }

	    // Attempts

	    if (otpEntity.getAttempts() >= 5) {

	        throw new IllegalArgumentException(
	                "Maximum OTP attempts exceeded");
	    }

	    // Increment attempt

	    otpEntity.setAttempts(
	            otpEntity.getAttempts() + 1);

	    // Verify hash

	    boolean valid =
	            passwordEncoder.matches(
	                    otp,
	                    otpEntity.getOtpHash());

	    if (!valid) {

	        otpRepository.save(otpEntity);

	        throw new IllegalArgumentException(
	                "Invalid OTP");
	    }

	    // Mark OTP as used

	    otpEntity.setVerified(true);

	    otpRepository.save(otpEntity);

	    return true;
	}
}
