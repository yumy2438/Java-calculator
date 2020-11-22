package userinterface;

import java.math.BigInteger;
import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import conversionservice.ConversionService;
import gui.CalculatorGUI;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Locale current_locale = new Locale("tr","TR");//Locale.getDefault();
		ServiceReference<?> serviceReference = context.getServiceReference(ConversionService.class);
		ConversionService service = (ConversionService) context.getService(serviceReference);
		CalculatorGUI.start_gui(service, current_locale);	
		System.out.println("Gui started.");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Gui stopped.");
	}

}
