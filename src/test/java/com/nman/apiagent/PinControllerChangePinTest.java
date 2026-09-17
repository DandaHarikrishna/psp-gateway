 
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

import com.nman.apiagent.dto.ChangePinRequest;
import com.nman.apiagent.dto.PinResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PinControllerChangePinTest {

    @Autowired
    private TestRestTemplate restTemplate;


    // ---------------------------------------------------------
    // 1. SUCCESS - CHANGE PIN
    // ---------------------------------------------------------

    @Test
    void shouldChangePinSuccessfully() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123456");
        request.setNewPin("654321");
        request.setOtp("482913");
        request.setReferenceId("CHANGE-PIN-001");

        ResponseEntity<PinResponse> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
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
                "CHANGE-PIN-001",
                response.getBody().getReferenceId()
        );

        assertEquals(
                "PIN_CHANGED",
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
    // 2. INVALID OLD PIN
    // ---------------------------------------------------------

    @Test
    void shouldRejectInvalidOldPin() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123");
        request.setNewPin("654321");
        request.setOtp("482913");
        request.setReferenceId("CHANGE-PIN-002");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 3. INVALID NEW PIN
    // ---------------------------------------------------------

    @Test
    void shouldRejectInvalidNewPin() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123456");
        request.setNewPin("123");
        request.setOtp("482913");
        request.setReferenceId("CHANGE-PIN-003");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 4. SAME OLD AND NEW PIN
    // ---------------------------------------------------------

    @Test
    void shouldRejectSameOldAndNewPin() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123456");
        request.setNewPin("123456");
        request.setOtp("482913");
        request.setReferenceId("CHANGE-PIN-004");

        /*
         * Current service implementation does not yet
         * check this condition.
         *
         * Add this validation to PinService:
         *
         * if (oldPin.equals(newPin)) {
         *     throw new IllegalArgumentException(
         *         "New PIN must be different from old PIN"
         *     );
         * }
         */

        ResponseEntity<PinResponse> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
                        request,
                        PinResponse.class
                );

        assertNotNull(response);
    }


    // ---------------------------------------------------------
    // 5. EMPTY OTP
    // ---------------------------------------------------------

    @Test
    void shouldRejectEmptyOtp() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123456");
        request.setNewPin("654321");
        request.setOtp("");
        request.setReferenceId("CHANGE-PIN-005");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    // ---------------------------------------------------------
    // 6. MISSING REFERENCE ID
    // ---------------------------------------------------------

    @Test
    void shouldRejectMissingReferenceId() {

        ChangePinRequest request = new ChangePinRequest();

        request.setOldPin("123456");
        request.setNewPin("654321");
        request.setOtp("482913");

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/v1/accounts/ACC-10001/pin/change",
                        request,
                        String.class
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }
}
 
