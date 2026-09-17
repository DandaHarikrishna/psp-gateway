 
package com.nman.apiagent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nman.apiagent.dto.PinResponse;
import com.nman.apiagent.dto.SetPinRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PinControllerSetPinTest {

    @Autowired
    private TestRestTemplate restTemplate;


    // ---------------------------------------------------------
    // 1. SUCCESS - SET PIN
    // ---------------------------------------------------------

    @Test
    void shouldSetPinSuccessfully() {

        SetPinRequest request = new SetPinRequest();

        request.setPin("123456");
        request.setOtp("482913");
        request.setReferenceId("SET-PIN-001");

        ResponseEntity<PinResponse> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/set",
                        request,
                        PinResponse.class
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
                "SET-PIN-001",
                response.getBody().getReferenceId()
        );

        assertEquals(
                "PIN_SET",
                response.getBody()
                        .getData()
                        .getStatus()
        );

        assertEquals(
                "ACC-10001",
                response.getBody()
                        .getData()
                        .getAccountId()
        );
    }


    // ---------------------------------------------------------
    // 2. INVALID PIN
    // ---------------------------------------------------------

    @Test
    void shouldRejectInvalidPin() {

        SetPinRequest request = new SetPinRequest();

        request.setPin("123");
        request.setOtp("482913");
        request.setReferenceId("SET-PIN-002");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/set",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 3. EMPTY PIN
    // ---------------------------------------------------------

    @Test
    void shouldRejectEmptyPin() {

        SetPinRequest request = new SetPinRequest();

        request.setPin("");
        request.setOtp("482913");
        request.setReferenceId("SET-PIN-003");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/set",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 4. INVALID OTP
    // ---------------------------------------------------------

    @Test
    void shouldRejectEmptyOtp() {

        SetPinRequest request = new SetPinRequest();

        request.setPin("123456");
        request.setOtp("");
        request.setReferenceId("SET-PIN-004");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/set",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 5. MISSING REFERENCE ID
    // ---------------------------------------------------------

    @Test
    void shouldRejectMissingReferenceId() {

        SetPinRequest request = new SetPinRequest();

        request.setPin("123456");
        request.setOtp("482913");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/set",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }
}
 
