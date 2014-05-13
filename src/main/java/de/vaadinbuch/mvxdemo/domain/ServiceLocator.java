package de.vaadinbuch.mvxdemo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Der <code>ServiceLocator</code> dient als zentraler Zugriffpunkt, um auf
 * Serviceinstanzen zuzugreifen. Daher ist der <code>ServiceLocator</code> als
 * Singleton implementiert.<br/>
 * über {@link #getInstance()} kann man auf die Systemweite Instanz des
 * <code>ServiceLocator</code>s zugreifen. Intern nutzt der
 * <code>ServiceLocator</code> den {@link ServiceLoader} um
 * {@link ServiceProvider} Instanzen zu laden, die die Serviceinstanzen zur
 * Verfügung stellen. Damit der <code>ServiceLocator</code> die
 * Implementierungen der <code>ServiceProvider</code> findet und die
 * entsprechenden Services zur Verfügung stellen kann, müssen die
 * vollqualifizierten Klassennamen der <code>ServiceProvider</code>
 * -Implementierungen in einer Datei mit dem vollqualifizierten Namen der
 * <code>ServiceProvider</code>-Klasse aufgelistet werden. Diese Datei muss im
 * Klassenpfad unter META-INF/services liegen.
 * 
 * @author Frank Hardy
 */
public class ServiceLocator {

	private static ServiceLocator instance;

	/**
	 * @return die Instanz des ServiceLocator-Singletons.
	 */
	public static ServiceLocator getInstance() {
		if (instance == null) {
			synchronized (ServiceLocator.class) {
				if (instance == null) {
					instance = new ServiceLocator();
				}
			}
		}
		return instance;
	}

	private final Map<Class<?>, ServiceProvider<?>> providersByType = new HashMap<Class<?>, ServiceProvider<?>>();

	@SuppressWarnings("rawtypes")
	private ServiceLocator() {
		ServiceLoader<ServiceProvider> serviceProviderLoader = ServiceLoader.load(ServiceProvider.class);
		for (ServiceProvider provider : serviceProviderLoader) {
			this.providersByType.put(provider.getServiceType(), provider);
		}
	}

	/**
	 * Liefert eine bestimmte Serviceinstanz.
	 * 
	 * @param serviceType
	 *            der Typ der zu liefernden Serviceinstanz.
	 * 
	 * @return die Serviceinstanz.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceType) {
		ServiceProvider<T> serviceProvider = (ServiceProvider<T>) this.providersByType.get(serviceType);
		if (serviceProvider != null) {
			return serviceProvider.getService();
		} else {
			throw new IllegalArgumentException("Unbekannter Service: " + serviceType.getName());
		}
	}
}
