package tdt4250.conversion.util;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.*;

import tdt4250.conversion.api.Unit;
import tdt4250.conversion.api.UnitSearchResult;

@Component(
		configurationPid = UnitConversion.FACTORY_PID,
		configurationPolicy = ConfigurationPolicy.REQUIRE
		)
public class UnitConversion implements Unit {

	public static final String FACTORY_PID = "tdt4250.dict3.util.UnitConversion";
	
	public static final String UNIT_CONVERSION_PROP = "unitConversion";
	public static final String UNIT_NAME_PROP = "unitName";
	
	private String name;
	private String conversion;
	
	@Override
	public String getUnitName() {
		return name;
	}

	protected void setUnitName(String name) {
		this.name = name;
	}
	
	protected void setUnitConversion(String conversion) {
		this.conversion = conversion;
	}
	
	public @interface UnitConfig {
		String unitName();
		String unitConversion() default "";
	}

	@Activate
	public void activate(BundleContext bc, UnitConfig config) {
		update(bc, config);
	}

	@Modified
	public void modify(BundleContext bc, UnitConfig config) {
		update(bc, config);		
	}

	protected void update(BundleContext bc, UnitConfig config) {
		setUnitName(config.unitName());
		setUnitConversion(config.unitConversion());
	}

	
	protected String getSuccessMessageStringFormat() {
		return this.name + " was succsessfully converted to %.2f " ;
	}

	protected String getFailureMessageStringFormat() {
		return this.name + " failed to convert!";
	}

	@Override
	public UnitSearchResult convert(String srcValue) {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("JavaScript");
		try {
			Double result = (Double) se.eval(String.valueOf(srcValue) + conversion);
			return new UnitSearchResult(true, String.format(getSuccessMessageStringFormat(), srcValue, result), null);
		} catch (ScriptException e) {
			return new UnitSearchResult(true, String.format(getFailureMessageStringFormat(), srcValue), null);
		}
	}

	@Override
	public String getUnitConversion() {
		return conversion;
	}
}	
