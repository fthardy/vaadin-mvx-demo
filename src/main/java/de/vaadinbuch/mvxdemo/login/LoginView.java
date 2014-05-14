package de.vaadinbuch.mvxdemo.login;

import de.vaadinbuch.mvxdemo.ViewAccessor;

/**
 * Die Interfacedefinition für die View der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public interface LoginView extends ViewAccessor {

	/**
	 * @return die Anmeldekomponente.
	 */
	LoginComponent getComponent();
}
