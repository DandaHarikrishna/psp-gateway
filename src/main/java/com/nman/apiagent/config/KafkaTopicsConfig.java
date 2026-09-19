package com.nman.apiagent.config;

public final class KafkaTopicsConfig {

    private KafkaTopicsConfig() {
    }

    // PSP -> UPI Switch
    public static final String UPI_VPA_REQUEST =
            "upi.vpa.request";

    // UPI Switch -> AxisBank PSP
    public static final String AXISBANK_VPA_REQUEST =
            "psp.axisbank.vpa.request";

    // AxisBank PSP -> UPI Switch
    public static final String UPI_VPA_RESPONSE =
            "upi.vpa.response";

    // UPI Switch -> AxisBank PSP
    public static final String AXISBANK_FINAL_RESPONSE =
            "psp.axisbank.vpa.final";
}