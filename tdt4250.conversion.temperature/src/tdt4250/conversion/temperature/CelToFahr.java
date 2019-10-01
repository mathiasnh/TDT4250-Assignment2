package tdt4250.conversion.temperature;

import org.osgi.service.component.annotations.Component;

import tdt4250.conversion.api.Converter;
import tdt4250.conversion.api.UnitConverter;

@Component 
public class CelToFahr extends UnitConverter implements Converter{
	
	public CelToFahr() {
		super("CelToFahr", "C", "F", "*1.8+32");
	}	
	
}
