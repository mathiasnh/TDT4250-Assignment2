package tdt4250.conversion.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

public class ConversionHandler {
	private List<Converter> converters = new ArrayList<>();

	public ConversionHandler(Converter... converters) {
		this.converters.addAll(Arrays.asList(converters));
	}

	public void addConverter(Converter converter) {
		this.converters.add(converter);
	}

	public void removeConverter(Converter converter) {
		this.converters.remove(converter);
	}

	public ConversionResult convert(String src, String tar, String value) {
		Converter converter = this.findMatchingConverter(src, tar);
		if(converter == null) {
			throw new MissingResourceException("Sorry, no suitable converter",
					this.getClass().getCanonicalName(), String.format("%s -> %s", src, tar));
		}
		return converter.convert(value);
	}

	private Converter findMatchingConverter(String src, String tar) {
		for (Converter uc : this.converters) {
			if (uc.getSrc().contentEquals(src) && uc.getTar().contentEquals(tar)) {
				return uc;
			}
		}
		return null;
	}
}

