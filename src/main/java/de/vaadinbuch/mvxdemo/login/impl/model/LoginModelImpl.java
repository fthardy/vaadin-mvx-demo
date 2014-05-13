package de.vaadinbuch.mvxdemo.login.impl.model;

import de.vaadinbuch.mvxdemo.domain.UserService;
import de.vaadinbuch.mvxdemo.login.LoginModel;

/**
 * Die Implementierung des Models der Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class LoginModelImpl implements LoginModel {

	private final UserService userService;

	/**
	 * Erzeugt eine neue Instanz dieses Models.
	 * 
	 * @param userService
	 *            die Instanz des Service aus der Domäne für den Zugriff auf die
	 *            Benutzer.
	 */
	public LoginModelImpl(UserService userService) {
		if (userService == null) {
			throw new NullPointerException("Undefinierter UserService!");
		}
		this.userService = userService;
	}

	@Override
	public int getMinUserIdLength() {
		return this.userService.getUserIdMinLength();
	}

	@Override
	public int getMinPasswordLength() {
		return this.userService.getPasswordMinLength();
	}

	@Override
	public boolean loginUser(String userId, String password) {
		return this.userService.checkCredentials(userId, password);
	}
}
