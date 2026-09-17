 
package com.nman.apiagent.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nman.apiagent.dto.CreateVpaRequest;
import com.nman.apiagent.dto.CreateVpaResponse;
import com.nman.apiagent.service.VpaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vpa")
public class VpaController {

	private final VpaService vpaService;

	public VpaController(VpaService vpaService) {
		this.vpaService = vpaService;
	}

	@PostMapping("/create")
	public ResponseEntity<CreateVpaResponse> createVpa(@Valid @RequestBody CreateVpaRequest request) {

		CreateVpaResponse response = vpaService.createVpa(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}