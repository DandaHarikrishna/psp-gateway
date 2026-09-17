package com.nman.apiagent.dto;

public class LinkAccountResponse {

    private boolean success;
    private String message;
    private String referenceId;
    private Data data;

    public LinkAccountResponse(
            boolean success,
            String message,
            String referenceId,
            Data data) {
        this.success = success;
        this.message = message;
        this.referenceId = referenceId;
        this.data = data;
    }

    public static class Data {

        private String accountNumber;
        private String ifsc;
        private String bankingName;
        private String accountHolderName;
        private String status;

        public Data(
                String accountNumber,
                String ifsc,
                String bankingName,
                String accountHolderName,
                String status) {

            this.accountNumber = accountNumber;
            this.ifsc = ifsc;
            this.bankingName = bankingName;
            this.accountHolderName = accountHolderName;
            this.status = status;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getIfsc() {
            return ifsc;
        }

        public String getBankingName() {
            return bankingName;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public String getStatus() {
            return status;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public Data getData() {
        return data;
    }
}