package com.nman.apiagent.dto;

public class VpaResponse {

    private String transactionId;

    private String vpa;

    private String handler;

    private String status;

    private String responseCode;

    private String message;

    public VpaResponse() {
    }

    public VpaResponse(
            String transactionId,
            String vpa,
            String handler,
            String status,
            String responseCode,
            String message) {

        this.transactionId = transactionId;
        this.vpa = vpa;
        this.handler = handler;
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
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

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(
            String responseCode) {

        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}