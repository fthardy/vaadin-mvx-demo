package de.vaadinbuch.mvxdemo.domain;

/**
 * Die Interfacedefinition eines Serviceproviders.<br/>
 * Ein Serviceprovider hat die Aufgabe eine Instanz eines bestimmten Services
 * auszuliefern.
 * 
 * @author Frank Hardy
 * 
 * @param <T>
 *            der Typ des Services.
 */
public interface ServiceProvider<T> {

	/**
	 * @return der Typ des Services, den der Serviceprovider ausliefert.
	 */
	Class<T> getServiceType();

	/**
	 * @return die Serviceinstanz.
	 */
	T getService();
}
