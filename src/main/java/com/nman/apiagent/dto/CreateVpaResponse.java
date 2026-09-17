package com.nman.apiagent.dto;

public class CreateVpaResponse {

    private boolean success;
    private String message;
    private String referenceId;
    private VpaData data;

    public CreateVpaResponse() {
    }

    public CreateVpaResponse(
            boolean success,
            String message,
            String referenceId,
            VpaData data) {

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

    public VpaData getData() {
        return data;
    }

    public void setData(VpaData data) {
        this.data = data;
    }

    public static class VpaData {

        private String vpaId;
        private String vpa;
        private String accountId;
        private String status;
        private boolean primary;

        public VpaData() {
        }

        public VpaData(
                String vpaId,
                String vpa,
                String accountId,
                String status,
                boolean primary) {

            this.vpaId = vpaId;
            this.vpa = vpa;
            this.accountId = accountId;
            this.status = status;
            this.primary = primary;
        }

        public String getVpaId() {
            return vpaId;
        }

        public void setVpaId(String vpaId) {
            this.vpaId = vpaId;
        }

        public String getVpa() {
            return vpa;
        }

        public void setVpa(String vpa) {
            this.vpa = vpa;
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

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }
    }
}
 
