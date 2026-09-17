 
package com.nman.apiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SendOtpRequest {

	@Pattern(regexp = "^[6-9][0-9]{9}$", 
			message = "Invalid mobile number")
	private String mobileNumber;

	@NotBlank(message = "Reference ID is required")
	private String referenceId;
	
	private String purpose;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
 
}
 
