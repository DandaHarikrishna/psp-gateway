package com.nman.apiagent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import com.nman.apiagent.dto.LinkAccountRequest;
import com.nman.apiagent.dto.LinkAccountResponse;

class LinkAccountControllerTest extends BaseIntegrationTest {

    @Test
    void shouldLinkBankAccountSuccessfully() {

        LinkAccountRequest request = new LinkAccountRequest();

        request.setName("Rahul Kumar");
        request.setMobileNumber("9876543210");
        request.setBankIfsc("HDFC0001234");
        request.setAccountNumber("123456789012");
        request.setReferenceId("REF-001");
        request.setConsent(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LinkAccountRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<LinkAccountResponse> response =
                restTemplate.postForEntity(
                        url("/api/v1/link-account"),
                        entity,
                        LinkAccountResponse.class
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertTrue(
                response.getBody().isSuccess()
        );

        assertEquals(
                "Bank account linked successfully",
                response.getBody().getMessage()
        );

        assertNotNull(
                response.getBody().getData()
        );
    }


    @Test
    void shouldRejectLinkAccountWithoutConsent() {

        LinkAccountRequest request = new LinkAccountRequest();

        request.setName("Rahul Kumar");
        request.setMobileNumber("9876543210");
        request.setBankIfsc("HDFC0001234");
        request.setAccountNumber("123456789012");
        request.setReferenceId("REF-002");

        request.setConsent(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LinkAccountRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/link-account"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
                        || response.getStatusCode().is5xxServerError()
        );
    }
}