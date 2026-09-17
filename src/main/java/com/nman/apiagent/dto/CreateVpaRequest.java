package com.nman.apiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateVpaRequest {

    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotBlank(message = "VPA is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]{2,50}@[a-zA-Z0-9.-]{2,30}$",
            message = "Invalid VPA format"
    )
    private String vpa;

    private boolean setAsPrimary;
    
    private String referenceId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public boolean isSetAsPrimary() {
        return setAsPrimary;
    }

    public void setSetAsPrimary(boolean setAsPrimary) {
        this.setAsPrimary = setAsPrimary;
    }

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}
 
