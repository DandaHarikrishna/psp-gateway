package com.nman.apiagent.fromupi;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nman.apiagent.config.KafkaTopicsConfig;
import com.nman.apiagent.dto.VpaRequest;
import com.nman.apiagent.service.VpaService;

import tools.jackson.databind.ObjectMapper;

@Component
public class AxisBankVpaRequestConsumer {

    private final ObjectMapper objectMapper;

    private final VpaService vpaService;

    public AxisBankVpaRequestConsumer(
            ObjectMapper objectMapper,
            VpaService vpaService) {

        this.objectMapper = objectMapper;
        this.vpaService = vpaService;
    }

    @KafkaListener(
            topics = KafkaTopicsConfig.AXISBANK_VPA_REQUEST,
            groupId = "axisbank-vpa-request-group"
    )
    public void consume(String message) {

        try {

            VpaRequest request =
                    objectMapper.readValue(
                            message,
                            VpaRequest.class
                    );

            System.out.println();
            System.out.println(
                    "========== STEP 2 =========="
            );

            System.out.println(
                    "UPI-SWITCH -> AXISBANK PSP"
            );

            System.out.println(
                    "Transaction : "
                    + request.getTransactionId()
            );

            System.out.println(
                    "VPA         : "
                    + request.getVpa()
            );

            vpaService.validateVpa(request);

        } catch (Exception e) {

            System.err.println(
                    "Unable to process VPA request: "
                    + e.getMessage()
            );
        }
    }
}