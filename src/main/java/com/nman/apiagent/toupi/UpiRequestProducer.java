package com.nman.apiagent.toupi;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.nman.apiagent.config.KafkaTopicsConfig;
import com.nman.apiagent.dto.VpaRequest;

import tools.jackson.databind.ObjectMapper;

@Component
public class UpiRequestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public UpiRequestProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(VpaRequest request) {

        try {

            String message =
                    objectMapper.writeValueAsString(
                            request
                    );

            System.out.println();
            System.out.println(
                    "========== STEP 1 =========="
            );

            System.out.println(
                    "PSP-SERVER -> UPI-SWITCH"
            );

            System.out.println(
                    "Transaction : "
                    + request.getTransactionId()
            );

            System.out.println(
                    "VPA         : "
                    + request.getVpa()
            );

            System.out.println(
                    "Topic       : "
                    + KafkaTopicsConfig.UPI_VPA_REQUEST
            );

            kafkaTemplate.send(
                    KafkaTopicsConfig.UPI_VPA_REQUEST,
                    request.getTransactionId(),
                    message
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to send VPA request",
                    e
            );
        }
    }
}