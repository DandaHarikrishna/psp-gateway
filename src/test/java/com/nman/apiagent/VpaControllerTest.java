package com.nman.apiagent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import com.nman.apiagent.dto.CreateVpaRequest;
import com.nman.apiagent.dto.CreateVpaResponse;

class VpaControllerTest extends BaseIntegrationTest {

    @Test
    void shouldCreateVpaSuccessfully() {

        CreateVpaRequest request =
                new CreateVpaRequest();

        request.setAccountId("ACC-123456");
        request.setVpa("rahul@bank");
        request.setSetAsPrimary(true);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<CreateVpaRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<CreateVpaResponse> response =
                restTemplate.postForEntity(
                        url("/api/v1/vpa"),
                        entity,
                        CreateVpaResponse.class
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
                "VPA created successfully",
                response.getBody().getMessage()
        );

        assertNotNull(
                response.getBody().getData()
        );

        assertNotNull(
                response.getBody()
                        .getData()
                        .getVpaId()
        );

        assertEquals(
                "rahul@bank",
                response.getBody()
                        .getData()
                        .getVpa()
        );

        assertEquals(
                "ACTIVE",
                response.getBody()
                        .getData()
                        .getStatus()
        );
    }


    @Test
    void shouldRejectInvalidAccount() {

        CreateVpaRequest request =
                new CreateVpaRequest();

        request.setAccountId("INVALID");
        request.setVpa("rahul2@bank");
        request.setSetAsPrimary(false);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<CreateVpaRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/vpa"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
        );
    }


    @Test
    void shouldRejectDuplicateVpa() {

        CreateVpaRequest request =
                new CreateVpaRequest();

        request.setAccountId("ACC-123456");
        request.setVpa("duplicate@bank");
        request.setSetAsPrimary(false);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<CreateVpaRequest> entity =
                new HttpEntity<>(request, headers);

        // First request
        restTemplate.postForEntity(
                url("/api/v1/vpa"),
                entity,
                CreateVpaResponse.class
        );

        // Second request
        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/vpa"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
        );
    }
}