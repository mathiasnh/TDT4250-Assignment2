package tdt4250.conversion.gogo;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import tdt4250.conversion.api.Converter;

public class Activator implements BundleActivator {
	private static Activator INSTANCE = null;
	
	private Map<String, ServiceRegistration<Converter>> converterServiceRegistrations = new HashMap<>();
	
	public static Activator getInstance() {
        return INSTANCE;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
	}

	
	public boolean addConverter(Converter converter) {
		boolean existed = removeConverter(converter.getName());
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceRegistration<Converter> serviceRegistration = bc.registerService(Converter.class, converter, null);
		converterServiceRegistrations.put(converter.getName(), serviceRegistration);
		return existed;
	}
	
	public boolean removeConverter(String name) {
		if(converterServiceRegistrations.containsKey(name)) {
			converterServiceRegistrations.get(name).unregister();
			converterServiceRegistrations.remove(name);
			return true;
		}
		return false;
	}
}

