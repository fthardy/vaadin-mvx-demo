package de.vaadinbuch.mvxdemo.login;

import de.vaadinbuch.mvxdemo.ViewAccessor;

/**
 * Die Interfacedefinition für die View der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public interface LoginView extends ViewAccessor {

	/**
	 * @return die eingegebene Benutzerkennung.
	 */
	String getUserId();

	/**
	 * @return das eingegebene Passwort.
	 */
	String getPassword();

	/**
	 * @param enabled
	 *            setzt den Aktivierungszustand des Anmeldebuttons.
	 */
	void setLoginButtonEnabled(boolean enabled);

	/**
	 * Setzt die View auf den Initialzustand zurück.
	 */
	void reset();
}
