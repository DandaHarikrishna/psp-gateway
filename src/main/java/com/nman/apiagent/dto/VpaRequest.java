package com.nman.apiagent.dto;

public class VpaRequest {

    private String transactionId;

    private String vpa;

    private String accountId;

    private String handler;

    public VpaRequest() {
    }

    public VpaRequest(
            String transactionId,
            String vpa,
            String accountId) {

        this.transactionId = transactionId;
        this.vpa = vpa;
        this.accountId = accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(
            String transactionId) {

        this.transactionId = transactionId;
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

    public void setAccountId(
            String accountId) {

        this.accountId = accountId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}