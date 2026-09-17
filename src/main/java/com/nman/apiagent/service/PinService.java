package com.nman.apiagent.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nman.apiagent.dto.ChangePinRequest;
import com.nman.apiagent.dto.PinResponse;
import com.nman.apiagent.dto.SetPinRequest;
import com.nman.apiagent.entity.AccountPin;
import com.nman.apiagent.repository.AccountPinRepository;
import com.nman.apiagent.repository.LinkedBankAccountRepository;
import com.nman.apiagent.repository.OtpRepository;

@Service
public class PinService {

	private final LinkedBankAccountRepository bankAccountRepository;

	private final AccountPinRepository pinRepository;

	private final OtpService otpService;
	
	private final OtpRepository otpRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public PinService(LinkedBankAccountRepository bankAccountRepository, AccountPinRepository pinRepository,
			OtpService otpService,OtpRepository otpRepository) {

		this.bankAccountRepository = bankAccountRepository;

		this.pinRepository = pinRepository;
		this.otpService = otpService;
		this.otpRepository=otpRepository;
	}

	@Transactional
	public PinResponse setPin(String accountId, SetPinRequest request) {

		validateAccount(accountId);

		// Check PIN is not already set

		if (pinRepository.existsByAccountId(accountId)) {

			throw new IllegalArgumentException("PIN is already set");
		}

		// Verify OTP

		otpService.verifyOtp(accountId, request.getOtpReferenceId(), request.getOtp());

		// Validate PIN

		validatePin(request.getPin());

		// Hash PIN

		String pinHash = passwordEncoder.encode(request.getPin());

		// Save PIN

		AccountPin accountPin = new AccountPin();

		accountPin.setAccountId(accountId);
		accountPin.setPinHash(pinHash);
		accountPin.setActive(true);

		pinRepository.save(accountPin);

		return buildResponse(accountId, request.getReferenceId(), "PIN_SET", "PIN set successfully");
	}

	@Transactional
	public PinResponse changePin(String accountId, ChangePinRequest request) {

		validateAccount(accountId);

		AccountPin accountPin = pinRepository.findByAccountId(accountId)
				.orElseThrow(() -> new IllegalArgumentException("PIN is not set"));

		// Verify OTP

		otpService.verifyOtp(accountId, request.getOtpReferenceId(), request.getOtp());

		// Validate old PIN

		if (!passwordEncoder.matches(request.getOldPin(), accountPin.getPinHash())) {

			throw new IllegalArgumentException("Old PIN is incorrect");
		}

		// Validate new PIN

		validatePin(request.getNewPin());

		// Prevent same PIN

		if (passwordEncoder.matches(request.getNewPin(), accountPin.getPinHash())) {

			throw new IllegalArgumentException("New PIN must be different from old PIN");
		}

		// Hash new PIN

		String newPinHash = passwordEncoder.encode(request.getNewPin());

		accountPin.setPinHash(newPinHash);
		accountPin.setActive(true);

		pinRepository.save(accountPin);

		return buildResponse(accountId, request.getReferenceId(), "PIN_CHANGED", "PIN changed successfully");
	}

	private void validateAccount(String accountId) {

		if (accountId == null || accountId.isBlank()) {

			throw new IllegalArgumentException("Account ID is required");
		}

		if (!bankAccountRepository.existsByAccountId(accountId)) {

			throw new IllegalArgumentException("Bank account not found: " + accountId);
		}
	}

	private void validatePin(String pin) {

		if (pin == null || pin.isBlank()) {

			throw new IllegalArgumentException("PIN is required");
		}

		if (!pin.matches("\\d{4,6}")) {

			throw new IllegalArgumentException("PIN must contain 4 to 6 digits");
		}
	}

	private PinResponse buildResponse(String accountId, String referenceId, String status, String message) {

		PinResponse.PinData data = new PinResponse.PinData(accountId, status);

		return new PinResponse(true, message, referenceId, data);
	}

}
