package conversionservice;
import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import conversionservice.impl.ConversionServiceImpl;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception 
	{
		Activator.context = bundleContext;
		System.out.println("Registry Service ConversionService...");
        this.registryConversionService();
		System.out.println("Conversion service started.");
	}
	
	private void registryConversionService() 
	{
		Locale current_locale = Locale.getDefault();
		ConversionService service = new ConversionServiceImpl(current_locale);
		context.registerService(ConversionService.class, service, null);
	}
	

	public void stop(BundleContext bundleContext) throws Exception 
	{
		Activator.context = null;
		System.out.println("Conversion service finished.");
	}

}
