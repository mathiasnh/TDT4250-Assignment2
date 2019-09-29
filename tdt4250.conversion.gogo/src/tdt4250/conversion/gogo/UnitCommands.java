package tdt4250.conversion.gogo;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.*;

import tdt4250.conversion.api.Unit;
import tdt4250.conversion.api.UnitSearchResult;
import tdt4250.conversion.util.UnitConversion;

@Component(
		service = UnitCommands.class,
		property = {
			"osgi.command.scope=unitConversion",
			"osgi.command.function=list",
			"osgi.command.function=add",
			"osgi.command.function=remove",
			"osgi.command.function=convert"
		}
	)
public class UnitCommands {

	private Configuration getConfig(String unitName) {
		try {
			Configuration[] configs = cm.listConfigurations("(&(" + UnitConversion.UNIT_NAME_PROP + "=" + unitName + ")(service.factoryPid=" + UnitConversion.FACTORY_PID + "))");
			if (configs != null && configs.length >= 1) {
				return configs[0];
			}
		} catch (IOException | InvalidSyntaxException e) {
		}
		return null;
	}

	public void list() {
		System.out.print("Unit conversions: ");
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			for (ServiceReference<Unit> serviceReference : bc.getServiceReferences(Unit.class, null)) {
				Unit unit = bc.getService(serviceReference);
				try {
					if (unit != null) {
						System.out.print(unit.getUnitName() + " = " + unit.getUnitConversion());
					}
				} finally {
					bc.ungetService(serviceReference);
				}
				System.out.print(" ");
			}
		} catch (InvalidSyntaxException e) {
		}
		System.out.println();
	}

	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	private ConfigurationAdmin cm;

	public void add(String tar, String expression) throws IOException {
		String actionName = "updated";
		Configuration config = getConfig(tar); // tar is the Target value, which couples as the name in this case. E.g F (fahrenheit) in F = C*1.8+32
		if (config == null) {
			config = cm.createFactoryConfiguration(UnitConversion.FACTORY_PID, "?");
			actionName = "added";
		}
		Dictionary<String, String> props = new Hashtable<>();
		props.put(UnitConversion.UNIT_NAME_PROP, tar);
		props.put(UnitConversion.UNIT_CONVERSION_PROP, expression);
		
		config.update(props);
		System.out.println(tar + " = " + expression + " " + actionName);
		
		
	}

	public void remove(String name) throws IOException, InvalidSyntaxException {
		Configuration config = getConfig(name);
		if (config != null) {
			config.delete();
		}
		System.out.println("Conversion for unit " + name + " was removed...");
	}
	
	public void convert(String name, String s) {
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			boolean found = false;
			for (ServiceReference<Unit> serviceReference : bc.getServiceReferences(Unit.class, null)) {
				Unit unit = bc.getService(serviceReference);
				if (unit != null) {
					if(name.equals(unit.getUnitName())) {
						found = true;
						try {
							UnitSearchResult search = unit.convert(s);
							System.out.println(search.getMessage());
						} finally {
							bc.ungetService(serviceReference);
						}
					}
				} else {
					System.out.println(serviceReference.getProperties());
				}
			}
			if(found == false) {
				System.out.println("Sorry, no conversions found with that name.");
			}
		} catch (InvalidSyntaxException e) {
		}
	}
}
