package com.nman.apiagent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import com.nman.apiagent.dto.ChangePinRequest;
import com.nman.apiagent.dto.PinResponse;
import com.nman.apiagent.dto.SetPinRequest;

class PinControllerTest extends BaseIntegrationTest {

    @Test
    void shouldSetPinSuccessfully() {

        String accountId = "ACC-123456";

        SetPinRequest request =
                new SetPinRequest();

        request.setPin("1234");
        request.setOtp("123456");
        request.setReferenceId("REF-PIN-001");

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<SetPinRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<PinResponse> response =
                restTemplate.postForEntity(
                        url("/api/v1/pin/"
                                + accountId
                                + "/set"),
                        entity,
                        PinResponse.class
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertTrue(
                response.getBody().isSuccess()
        );

        assertEquals(
                "PIN set successfully",
                response.getBody().getMessage()
        );

        assertNotNull(
                response.getBody().getData()
        );

        assertEquals(
                accountId,
                response.getBody()
                        .getData()
                        .getAccountId()
        );

        assertEquals(
                "PIN_SET",
                response.getBody()
                        .getData()
                        .getStatus()
        );
    }


    @Test
    void shouldRejectSetPinWithoutOtp() {

        String accountId = "ACC-123456";

        SetPinRequest request =
                new SetPinRequest();

        request.setPin("1234");
        request.setOtp(null);
        request.setReferenceId("REF-PIN-002");

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<SetPinRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/pin/"
                                + accountId
                                + "/set"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
        );
    }


    @Test
    void shouldChangePinSuccessfully() {

        String accountId = "ACC-123456";

        ChangePinRequest request =
                new ChangePinRequest();

        request.setOldPin("1234");
        request.setNewPin("5678");
        request.setOtp("123456");
        request.setReferenceId("REF-PIN-003");

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<ChangePinRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<PinResponse> response =
                restTemplate.postForEntity(
                        url("/api/v1/pin/"
                                + accountId
                                + "/change"),
                        entity,
                        PinResponse.class
                );

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertTrue(
                response.getBody().isSuccess()
        );

        assertEquals(
                "PIN changed successfully",
                response.getBody().getMessage()
        );

        assertEquals(
                "PIN_CHANGED",
                response.getBody()
                        .getData()
                        .getStatus()
        );
    }


    @Test
    void shouldRejectChangePinWithoutOtp() {

        String accountId = "ACC-123456";

        ChangePinRequest request =
                new ChangePinRequest();

        request.setOldPin("1234");
        request.setNewPin("5678");
        request.setOtp(null);
        request.setReferenceId("REF-PIN-004");

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<ChangePinRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url("/api/v1/pin/"
                                + accountId
                                + "/change"),
                        entity,
                        String.class
                );

        assertTrue(
                response.getStatusCode().is4xxClientError()
        );
    }
}