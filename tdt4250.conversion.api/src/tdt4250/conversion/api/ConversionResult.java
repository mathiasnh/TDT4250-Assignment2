package tdt4250.conversion.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public class ConversionResult {
	private boolean success;
	private String message = "Sorry, no suitable converter"; // Default message
	
	public ConversionResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
}

