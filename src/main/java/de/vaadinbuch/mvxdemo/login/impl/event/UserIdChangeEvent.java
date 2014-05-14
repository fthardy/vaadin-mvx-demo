package de.vaadinbuch.mvxdemo.login.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginView;

/**
 * Signalisiert die Änderung der Benutzerkennung von der View zum Presenter der
 * Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class UserIdChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final String currentUserId;

	/**
	 * Erzeugt eine neue Instanz dieses Events.
	 * 
	 * @param view
	 *            die View der Anmeldekomponente, die dieses Event versendet.
	 * @param userId
	 *            die neue Benutzerkennung.
	 */
	public UserIdChangeEvent(LoginView view, String userId) {
		super(view);
		this.currentUserId = userId;
	}

	/**
	 * @return die neue Benutzerkennung.
	 */
	public String getCurrentUserId() {
		return this.currentUserId;
	}
}
