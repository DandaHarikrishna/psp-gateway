 
package com.nman.apiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePinRequest {

    @NotBlank(message = "Old PIN is required")
    @Pattern(
            regexp = "^[0-9]{4,6}$",
            message = "Old PIN must contain 4 to 6 digits"
    )
    private String oldPin;

    @NotBlank(message = "New PIN is required")
    @Pattern(
            regexp = "^[0-9]{4,6}$",
            message = "New PIN must contain 4 to 6 digits"
    )
    private String newPin;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "Reference ID is required")
    private String referenceId;

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
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
		// TODO Auto-generated method stub
		return null;
	}
}
 
