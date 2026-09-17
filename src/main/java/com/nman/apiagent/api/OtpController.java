package com.nman.apiagent.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nman.apiagent.dto.SendOtpRequest;
import com.nman.apiagent.dto.SendOtpResponse;
import com.nman.apiagent.service.OtpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/{accountId}/pin/otp")
    public ResponseEntity<SendOtpResponse> sendOtp(
            @PathVariable String accountId,
            @Valid @RequestBody SendOtpRequest request) {

        SendOtpResponse response =
                otpService.sendOtp(accountId, request);

        return ResponseEntity.ok(response);
    }
}
 
