package de.vaadinbuch.mvxdemo.login.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginComponent;

/**
 * Signalisiert einen fehlgeschlagenen Anmeldeversuch aus der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class LoginFailedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine neue Instanz dieses Events.
	 * 
	 * @param component
	 *            die Anmeldekomponente, die dieses Event versendet.
	 */
	public LoginFailedEvent(LoginComponent component) {
		super(component);
	}
}
