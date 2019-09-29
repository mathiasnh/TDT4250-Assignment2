package tdt4250.conversion.api;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Unit {
	String getUnitName();
	String getUnitConversion();
	UnitSearchResult convert(String srcValue);
}
