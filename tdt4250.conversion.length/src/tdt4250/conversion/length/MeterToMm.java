package tdt4250.conversion.length;

import org.osgi.service.component.annotations.*;

import tdt4250.conversion.api.Converter;
import tdt4250.conversion.api.UnitConverter;

@Component
public class MeterToMm extends UnitConverter implements Converter {

	public MeterToMm() {
		super("MeterToMm", "m", "mm", "*1000");
	}
}
