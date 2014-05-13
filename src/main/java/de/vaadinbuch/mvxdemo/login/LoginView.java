package de.vaadinbuch.mvxdemo.login;

import de.vaadinbuch.mvxdemo.ViewAccessor;

/**
 * Die Interfacedefinition für die View der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public interface LoginView extends ViewAccessor {

	/**
	 * Ein Callbackinterface zur Rückmeldung von Ereignissen aus der View zum
	 * Presenter.<br/>
	 * Dieses Interface ist exklusiv für den Presenter und muss vom diesem
	 * implementiert werden. Damit die View den Presenter informieren kann, muss
	 * der Presenter sich über {@link LoginView#setPresenter(Presenter)} an der
	 * View registrieren.
	 * 
	 * @author Frank Hardy
	 */
	interface Presenter {

		/**
		 * Wird aufgerufen, wenn die Benutzerkennung im Eingabefeld verändert
		 * wird.
		 * 
		 * @param currentUid
		 *            der aktuelle Eingabewert der Benutzerkennung.
		 */
		void onUserIdChange(String currentUid);

		/**
		 * Wird aufgerufen, wenn das Passwort im Eingabefeld verändert wird.
		 * 
		 * @param currentPw
		 *            der aktuelle Eingabewert des Passworts.
		 */
		void onPasswordChange(String currentPw);

		/**
		 * Wird aufgerufen, wenn der Anmeldebutton geklickt wird.
		 */
		void onLogin();
	}

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

	/**
	 * Registriert den Presenter an der View.
	 * 
	 * @param presenter
	 *            die Presenterinstanz der Anmeldekomponente.
	 */
	void setPresenter(Presenter presenter);
}
