package de.vaadinbuch.mvxdemo.login.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginComponent;

/**
 * Signalisiert einen erfolgreichen Anmeldeversuch aus der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class LoginSuccessEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final String userId;

	/**
	 * Erzeugt eine neue Instanz dieses Events.
	 * 
	 * @param component
	 *            die View der Anmeldekomponente, die dieses Event versendet.
	 * @param userId
	 *            die Benutzerkennung des angemeldeten Benutzers.
	 */
	public LoginSuccessEvent(LoginComponent component, String userId) {
		super(component);
		this.userId = userId;
	}

	/**
	 * @return die Benutzerkennung des angemeldeten Benutzers.
	 */
	public String getUserId() {
		return this.userId;
	}
}
