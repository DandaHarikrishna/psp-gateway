 
package com.nman.apiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SetPinRequest {

    @NotBlank(message = "PIN is required")
    @Pattern(
            regexp = "^[0-9]{4,6}$",
            message = "PIN must contain 4 to 6 digits"
    )
    private String pin;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "Reference ID is required")
    private String referenceId;
    
    private String otpReferenceId;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

	public String getOtpReferenceId() {
		return otpReferenceId;
	}

	public void setOtpReferenceId(String otpReferenceId) {
		this.otpReferenceId = otpReferenceId;
	}

	 
}
 
