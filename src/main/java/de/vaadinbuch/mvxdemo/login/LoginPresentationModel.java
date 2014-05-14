package de.vaadinbuch.mvxdemo.login;

/**
 * Die Schnittstellendefinition des Modells einer Anmeldeoberfläche.
 * 
 * @author Frank Hardy
 */
public interface LoginPresentationModel {

	/**
	 * @return die Benutzerkennung.
	 */
	String getUserId();

	/**
	 * @param userId
	 *            die neue Benutzerkennung.
	 */
	void setUserId(String userId);

	/**
	 * @return das Passwort
	 */
	String getPassword();

	/**
	 * @param password
	 *            das neue Passwort
	 */
	void setPassword(String password);

	/**
	 * @return <code>true</code> wenn ein Anmeldeversuch durchgeführt werden
	 *         darf.
	 */
	boolean isLoginEnabled();

	/**
	 * Wird von der Anmeldeoberfläche aufgerufen, wenn die Benutzerkennung
	 * geändert werden soll.
	 * 
	 * @param currentUserId
	 *            die aktuelle Benutzerkennung.
	 */
	void onUserIdChange(String currentUserId);

	/**
	 * Wird von der Anmeldeoberfläche aufgerufen, wenn das Passwort geändert
	 * werden soll.
	 * 
	 * @param currentPW
	 *            das aktuelle Passwort.
	 */
	void onPasswordChange(String currentPW);

	/**
	 * Wird von der Anmeldeoberfläche aufgerufen, wenn der Button zur Anmeldung
	 * geklickt wurde.
	 */
	void onLogin();
}
