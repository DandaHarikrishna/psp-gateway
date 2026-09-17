 
package com.nman.apiagent.dto;

public class SendOtpResponse {

    private boolean success;
    private String message;
    private String referenceId;
    private OtpData data;

    public SendOtpResponse() {
    }

    public SendOtpResponse(
            boolean success,
            String message,
            String referenceId,
            OtpData data) {

        this.success = success;
        this.message = message;
        this.referenceId = referenceId;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public OtpData getData() {
        return data;
    }

    public void setData(OtpData data) {
        this.data = data;
    }

    public static class OtpData {

        private String otpReferenceId;
        private String maskedMobileNumber;
        private int expiresInSeconds;

        public OtpData() {
        }

        public OtpData(
                String otpReferenceId,
                String maskedMobileNumber,
                int expiresInSeconds) {

            this.otpReferenceId = otpReferenceId;
            this.maskedMobileNumber = maskedMobileNumber;
            this.expiresInSeconds = expiresInSeconds;
        }

        public String getOtpReferenceId() {
            return otpReferenceId;
        }

        public void setOtpReferenceId(String otpReferenceId) {
            this.otpReferenceId = otpReferenceId;
        }

        public String getMaskedMobileNumber() {
            return maskedMobileNumber;
        }

        public void setMaskedMobileNumber(String maskedMobileNumber) {
            this.maskedMobileNumber = maskedMobileNumber;
        }

        public int getExpiresInSeconds() {
            return expiresInSeconds;
        }

        public void setExpiresInSeconds(int expiresInSeconds) {
            this.expiresInSeconds = expiresInSeconds;
        }
    }
}
 
