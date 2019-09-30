package tdt4250.conversion.api;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class UnitConverter implements Converter {

	private String name;
	private String src;
	private String tar;
	private String expression;
	
	public UnitConverter(String name, String src, String tar, String expression) {
		super();
		this.name = name;
		this.src = src;
		this.tar = tar;
		this.expression = expression;
	}

	public String getSrc() {
		return src;
	}

	public String getTar() {
		return tar;
	}
	
	public String getExpression() {
		return expression;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ConversionResult convert(String srcValue) {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("JavaScript");
		try {
			//se.put(this.getSrc(), String.valueOf(srcValue));
			System.out.println(String.valueOf(srcValue) + expression);
			double result = (double) se.eval(String.valueOf(srcValue) + expression);
			return new ConversionResult(true, String.format(getSuccessMessageStringFormat(), srcValue, result));
		} catch (ScriptException e) {
			return new ConversionResult(true, String.format(getFailureMessageStringFormat()));
		}
	}
	
	protected String getSuccessMessageStringFormat() {
		return "%s" + this.getSrc() + " was succsessfully converted to %.2f" + this.getTar() ;
	}

	protected String getFailureMessageStringFormat() {
		return this.name + " failed to convert!";
	}

	
}
