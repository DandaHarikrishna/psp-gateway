package com.nman.apiagent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import com.nman.apiagent.dto.SendOtpRequest;
import com.nman.apiagent.dto.SendOtpResponse;

class OtpControllerTest extends BaseIntegrationTest {

    @Test
    void shouldSendOtpSuccessfully() {

        String accountId = "ACC-123456";

        SendOtpRequest request = new SendOtpRequest();

        request.setMobileNumber("9876543210");
        request.setReferenceId("REF-OTP-001");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SendOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<SendOtpResponse> response =
                restTemplate.postForEntity(
                        url("/api/v1/otp/" + accountId),
                        entity,
                        SendOtpResponse.class
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
                "OTP sent successfully",
                response.getBody().getMessage()
        );

        assertNotNull(
                response.getBody().getData()
        );

        assertNotNull(
                response.getBody()
                        .getData()
                        .getOtpReferenceId()
        );
    }


    @Test
    void shouldRejectOtpWhenAccountIdMissing() {

        SendOtpRequest request = new SendOtpRequest();

        request.setMobileNumber("9876543210");
        request.setReferenceId("REF-OTP-002");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SendOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/otp/"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
        );
    }
}