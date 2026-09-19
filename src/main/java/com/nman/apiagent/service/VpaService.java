package com.nman.apiagent.service;

import org.springframework.stereotype.Service;

import com.nman.apiagent.dto.VpaRequest;
import com.nman.apiagent.dto.VpaResponse;
import com.nman.apiagent.toupi.UpiResponseProducer;

@Service
public class VpaService {

    private final UpiResponseProducer responseProducer;

    public VpaService(
            UpiResponseProducer responseProducer) {

        this.responseProducer = responseProducer;
    }

    public void validateVpa(
            VpaRequest request) {

        boolean valid =
                isValidVpa(request);

        VpaResponse response;

        if (valid) {

            response =
                    new VpaResponse(
                            request.getTransactionId(),
                            request.getVpa(),
                            "axisbank",
                            "SUCCESS",
                            "00",
                            "VPA validated successfully"
                    );

        } else {

            response =
                    new VpaResponse(
                            request.getTransactionId(),
                            request.getVpa(),
                            "axisbank",
                            "FAILURE",
                            "01",
                            "VPA validation failed"
                    );
        }

        responseProducer.send(response);
    }

    private boolean isValidVpa(
            VpaRequest request) {

        /*
         * Temporary PSP simulation.
         *
         * Later replace this with:
         *
         * VpaRepository
         * PostgreSQL
         */

        return request.getVpa() != null
                && request.getVpa()
                          .endsWith("@axisbank")
                && request.getAccountId() != null
                && !request.getAccountId()
                           .isBlank();
    }
}