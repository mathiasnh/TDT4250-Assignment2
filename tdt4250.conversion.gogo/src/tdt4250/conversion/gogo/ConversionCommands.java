package tdt4250.conversion.gogo;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.MissingResourceException;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.*;

import tdt4250.conversion.api.ConversionResult;
import tdt4250.conversion.api.Converter;
import tdt4250.conversion.api.UnitConverter;

@Component(
		service = ConversionCommands.class,
		property = {
			"osgi.command.scope=unitConversion",
			"osgi.command.function=list",
			"osgi.command.function=add",
			"osgi.command.function=remove",
			"osgi.command.function=convert"
		}
	)
public class ConversionCommands {

	public void list() {
		System.out.println("Available unit conversions: ");
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			for (ServiceReference<Converter> serviceReference : bc.getServiceReferences(Converter.class, null)) {
				Converter converter = bc.getService(serviceReference);
				try {
					if (converter != null) {
						System.out.println(converter.getTar() + " = " + converter.getExpression());
					}
				} finally {
					bc.ungetService(serviceReference);
				}
			}
		} catch (InvalidSyntaxException e) {}
	}

	public void add(@Descriptor("Name") String name,
					@Descriptor("Target unit") String target,
					@Descriptor("Source unit") String source,
					@Descriptor("Expression") String expression) {
		Converter converter = new UnitConverter(name, source, target, expression);
		boolean existed = Activator.getInstance().addConverter(converter);
		System.out.println(converter.getName() + " " + (existed ? "replaced" : "added"));
	}

	public String remove(@Descriptor("name") String name) {
		boolean removed = Activator.getInstance().removeConverter(name);
		return (removed ? "removed:" : "not added manually:") + " " + name;
	}

	public void convert(@Descriptor("Source unit") String source,
						  @Descriptor("Value") String value,
						  @Descriptor("Target unit") String target) {
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			for (ServiceReference<Converter> serviceReference : bc.getServiceReferences(Converter.class, null)) {
				Converter converter = bc.getService(serviceReference);
				if (converter != null) {
					if (converter.getSrc().contentEquals(source) && converter.getTar().contentEquals(target)) {
						System.out.println(converter.convert(value).getMessage());
					}
				} else {
					throw new MissingResourceException("Sorry, no suitable converter",
							this.getClass().getCanonicalName(), String.format("%s -> %s", source, target));
				}
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}
}

