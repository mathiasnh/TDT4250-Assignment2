package tdt4250.conversion.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UnitSearch {
	private static final String DEFAULT_MESSAGE = "No conversions found";
	private Collection<Unit> conversions = new ArrayList<Unit>();
	
	public void addConversion(Unit unit) {
		conversions.add(unit);
	}

	public void removeConversion(Unit unit) {
		conversions.remove(unit);
	}
	
	public UnitSearch(Unit... units) {
		conversions.addAll(Arrays.asList(units));
	}
	
	private UnitSearchResult search(String expression, Iterable<Unit> conversions) {
		StringBuilder messages = new StringBuilder();
		URI link = null;
		boolean success = false;
		for(Unit conversion: conversions) {
			UnitSearchResult result = conversion.convert(expression);
			
			if (result.isSuccess()) {
				messages.append(result.getMessage());
				success = true;
				if (link == null) {
					link = result.getLink();
				}
				break;
			}else {
				messages.append(result.getMessage());
			}
		}
		if (messages.length() == 0) {
			messages.append(DEFAULT_MESSAGE);
		}
		System.out.println(messages);
		return new UnitSearchResult(success, messages.toString(), link);
	}

	public UnitSearchResult search(String unitKey, String expression) {
		return search(expression, conversions.stream().filter(unit -> unit.getUnitName().equals(unitKey)).collect(Collectors.toList()));
	}
	
	public UnitSearchResult search(String expression) {
		return search(expression, conversions);
	}
	
}
