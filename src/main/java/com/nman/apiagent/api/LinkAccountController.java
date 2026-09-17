package com.nman.apiagent.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nman.apiagent.dto.LinkAccountRequest;
import com.nman.apiagent.dto.LinkAccountResponse;
import com.nman.apiagent.service.LinkAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LinkAccountController {

    private final LinkAccountService linkAccountService;

    public LinkAccountController(
            LinkAccountService linkAccountService) {
        this.linkAccountService = linkAccountService;
    }

    @PostMapping("/link-accounts")
    public ResponseEntity<LinkAccountResponse> linkAccount(
            @Valid @RequestBody LinkAccountRequest request) {

        LinkAccountResponse response =
                linkAccountService.linkAccount(request);

        return ResponseEntity.ok(response);
    }
}