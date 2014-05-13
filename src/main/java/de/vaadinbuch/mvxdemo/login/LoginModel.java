package de.vaadinbuch.mvxdemo.login;

/**
 * Die Interfacedefinition des Models der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public interface LoginModel {

	/**
	 * @return die minimale Länge der Benutzerkennung.
	 */
	int getMinUserIdLength();

	/**
	 * @return die minimale Länge des Passworts.
	 */
	int getMinPasswordLength();

	/**
	 * Führt den Anmeldevorgang durch.
	 * 
	 * @param userId
	 *            die Kennung des anzumeldenden Benutzers.
	 * @param password
	 *            das Passwort des anzumeldenden Benutzers.
	 * 
	 * @return <code>true</code>, wenn der Anmeldevorgang erfolgreich war.
	 *         Andernfalls <code>false</code>.
	 */
	boolean loginUser(String userId, String password);
}
