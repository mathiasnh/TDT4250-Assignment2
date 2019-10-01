package tdt4250.conversion.weight;

import org.osgi.service.component.annotations.*;

import tdt4250.conversion.api.Converter;
import tdt4250.conversion.api.UnitConverter;

@Component
public class LbsToKg extends UnitConverter implements Converter {

	public LbsToKg() {
		super("LbsTokg", "lbs", "kg", "*0.45359237");
	}
}
