package com.nman.apiagent.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nman.apiagent.dto.ChangePinRequest;
import com.nman.apiagent.dto.PinResponse;
import com.nman.apiagent.dto.SetPinRequest;
import com.nman.apiagent.service.PinService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class PinController {

    private final PinService pinService;

    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @PostMapping("/{accountId}/pin/set")
    public ResponseEntity<PinResponse> setPin(
            @PathVariable String accountId,
            @Valid @RequestBody SetPinRequest request) {

        PinResponse response =
                pinService.setPin(accountId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/{accountId}/pin/change")
    public ResponseEntity<PinResponse> changePin(
            @PathVariable String accountId,
            @Valid @RequestBody ChangePinRequest request) {

        PinResponse response =
                pinService.changePin(accountId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
 
