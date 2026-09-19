package com.nman.apiagent.toupi;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.nman.apiagent.config.KafkaTopicsConfig;
import com.nman.apiagent.dto.VpaResponse;

import tools.jackson.databind.ObjectMapper;

@Component
public class UpiResponseProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public UpiResponseProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(VpaResponse response) {

        try {

            String message =
                    objectMapper.writeValueAsString(
                            response
                    );

            System.out.println();
            System.out.println(
                    "========== STEP 3 =========="
            );

            System.out.println(
                    "AXISBANK PSP -> UPI-SWITCH"
            );

            System.out.println(
                    "Transaction : "
                    + response.getTransactionId()
            );

            System.out.println(
                    "Status      : "
                    + response.getStatus()
            );

            System.out.println(
                    "ResponseCode: "
                    + response.getResponseCode()
            );

            System.out.println(
                    "Topic       : "
                    + KafkaTopicsConfig.UPI_VPA_RESPONSE
            );

            kafkaTemplate.send(
                    KafkaTopicsConfig.UPI_VPA_RESPONSE,
                    response.getTransactionId(),
                    message
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to send VPA response",
                    e
            );
        }
    }
}