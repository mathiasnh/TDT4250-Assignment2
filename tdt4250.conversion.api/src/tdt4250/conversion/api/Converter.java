package tdt4250.conversion.api;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Converter {
	public String getName();
	public String getSrc();
	public String getTar();
	public String getExpression();
	public ConversionResult convert(String value);
}

