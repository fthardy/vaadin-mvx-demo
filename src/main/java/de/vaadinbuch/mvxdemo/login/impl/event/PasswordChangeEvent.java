package de.vaadinbuch.mvxdemo.login.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginView;

/**
 * Signalisiert die Änderung des Passwortes von der View zum Presenter der
 * Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class PasswordChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final String currentPassword;

	/**
	 * Erzeugt eine neue Instanz dieses Events.
	 * 
	 * @param view
	 *            die View der Anmeldekomponente, die dieses Event versendet.
	 * @param password
	 *            das neue Passwort.
	 */
	public PasswordChangeEvent(LoginView view, String password) {
		super(view);
		this.currentPassword = password;
	}

	/**
	 * @return das neue Passwort.
	 */
	public String getCurrentPassword() {
		return this.currentPassword;
	}
}
