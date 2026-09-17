 
package com.nman.apiagent.dto;

public class PinResponse {

    private boolean success;
    private String message;
    private String referenceId;
    private PinData data;

    public PinResponse() {
    }

    public PinResponse(
            boolean success,
            String message,
            String referenceId,
            PinData data) {

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

    public PinData getData() {
        return data;
    }

    public void setData(PinData data) {
        this.data = data;
    }

    public static class PinData {

        private String accountId;
        private String status;

        public PinData() {
        }

        public PinData(String accountId, String status) {
            this.accountId = accountId;
            this.status = status;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
 
