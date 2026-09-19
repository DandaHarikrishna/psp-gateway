package com.nman.apiagent.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nman.apiagent.dto.VpaRequest;
import com.nman.apiagent.toupi.UpiRequestProducer;

@RestController
@RequestMapping("/api/vpa")
public class VpaController {

    private final UpiRequestProducer producer;

    public VpaController(
            UpiRequestProducer producer) {

        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> validateVpa(
            @RequestBody VpaRequest request) {

        producer.send(request);

        return ResponseEntity
                .accepted()
                .body(
                        "VPA request submitted. "
                        + "TransactionId: "
                        + request.getTransactionId()
                );
    }
}