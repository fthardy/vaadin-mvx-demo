package de.vaadinbuch.mvxdemo.login.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginView;

/**
 * Signalisiert einen Anmeldeversuch von der View zum Presenter der
 * Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class LoginAttemptEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine neue Instanz dieses Events.
	 * 
	 * @param view
	 *            die View der Anmeldekomponente, die dieses Event versendet.
	 */
	public LoginAttemptEvent(LoginView view) {
		super(view);
	}
}
