package com.nman.apiagent.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nman.apiagent.dto.CreateVpaRequest;
import com.nman.apiagent.dto.CreateVpaResponse;
import com.nman.apiagent.entity.Vpa;
import com.nman.apiagent.repository.LinkedBankAccountRepository;
import com.nman.apiagent.repository.VpaRepository;

@Service
public class VpaService {

	private final LinkedBankAccountRepository bankAccountRepository;
	private final VpaRepository vpaRepository;

	public VpaService(LinkedBankAccountRepository bankAccountRepository, VpaRepository vpaRepository) {

		this.bankAccountRepository = bankAccountRepository;
		this.vpaRepository = vpaRepository;
	}

	@Transactional
	public CreateVpaResponse createVpa(CreateVpaRequest request) {

		// 1. Validate account

		boolean accountExists = bankAccountRepository.existsByAccountId(request.getAccountId());

		if (!accountExists) {
			throw new IllegalArgumentException("Bank account not found: " + request.getAccountId());
		}

		// 2. Check VPA already exists

		if (vpaRepository.existsByVpa(request.getVpa())) {

			throw new IllegalArgumentException("VPA already exists: " + request.getVpa());
		}

		// 3. Generate VPA ID

		String vpaId = "VPA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		// 4. Create entity

		Vpa vpa = new Vpa();

		vpa.setVpa(request.getVpa());
		vpa.setAccountId(request.getAccountId());
		vpa.setStatus("ACTIVE");
		vpa.setPrimaryVpa(request.isSetAsPrimary());

		// 5. Save into database

		vpaRepository.save(vpa);

		// 6. Response

		CreateVpaResponse.VpaData data = new CreateVpaResponse.VpaData(vpaId, request.getVpa(), request.getAccountId(),
				"ACTIVE", request.isSetAsPrimary());

		return new CreateVpaResponse(true, "VPA created successfully", "", data);
	}
}
