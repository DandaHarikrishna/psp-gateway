package com.nman.apiagent.fromupi;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nman.apiagent.config.KafkaTopicsConfig;
import com.nman.apiagent.dto.VpaResponse;

import tools.jackson.databind.ObjectMapper;

@Component
public class FinalResponseConsumer {

    private final ObjectMapper objectMapper;

    public FinalResponseConsumer(
            ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = KafkaTopicsConfig.AXISBANK_FINAL_RESPONSE,
            groupId = "axisbank-final-response-group"
    )
    public void consume(String message) {

        try {

            VpaResponse response =
                    objectMapper.readValue(
                            message,
                            VpaResponse.class
                    );

            System.out.println();
            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "========== STEP 4 ================"
            );

            System.out.println(
                    "UPI-SWITCH -> AXISBANK PSP"
            );

            System.out.println(
                    "FINAL VPA RESPONSE"
            );

            System.out.println(
                    "----------------------------------"
            );

            System.out.println(
                    "Transaction ID : "
                    + response.getTransactionId()
            );

            System.out.println(
                    "VPA            : "
                    + response.getVpa()
            );

            System.out.println(
                    "Handler        : "
                    + response.getHandler()
            );

            System.out.println(
                    "Status         : "
                    + response.getStatus()
            );

            System.out.println(
                    "Response Code  : "
                    + response.getResponseCode()
            );

            System.out.println(
                    "Message        : "
                    + response.getMessage()
            );

            System.out.println(
                    "=================================="
            );

        } catch (Exception e) {

            System.err.println(
                    "Unable to process final response: "
                    + e.getMessage()
            );
        }
    }
}