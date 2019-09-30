package tdt4250.conversion.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public class ConversionResult {
	private final boolean success;
	private final String message;
	
	public ConversionResult(final boolean success, final String message) {
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

