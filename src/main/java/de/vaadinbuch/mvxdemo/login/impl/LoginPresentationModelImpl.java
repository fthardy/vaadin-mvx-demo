package de.vaadinbuch.mvxdemo.login.impl;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.LoginPresentationModel;
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginEnabledChangedEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.ResetLoginViewEvent;

/**
 * Die Implementierung des Presentationmodel.
 * 
 * @author Frank Hardy
 */
public class LoginPresentationModelImpl implements LoginPresentationModel, LoginComponent {

	private final LoginModel model;

	private String userId = "";
	private String password = "";
	private boolean loginEnabled;

	@Inject
	Event<LoginSuccessEvent> loginSuccessEventSink;
	@Inject
	Event<LoginFailedEvent> loginFailedEventSink;
	@Inject
	Event<ResetLoginViewEvent> resetLoginViewEventSink;
	@Inject
	Event<LoginEnabledChangedEvent> loginEnabledChangedEventSink;

	/**
	 * Erzeugt eine neue Instanz der Loginkomponente.
	 * 
	 * @param model
	 *            das Model für der Komponente.
	 */
	@Inject
	public LoginPresentationModelImpl(LoginModel model) {
		this.model = model;
	}

	@Override
	public void onUserIdChange(String currentUserId) {
		this.updateLoginButtonState(currentUserId, this.password);
	}

	@Override
	public void onPasswordChange(String currentPw) {
		this.updateLoginButtonState(this.userId, currentPw);
	}

	@Override
	public void onLogin() {
		if (this.model.loginUser(this.userId, this.password)) {
			this.loginSuccessEventSink.fire(new LoginSuccessEvent(this, this.userId));
		} else {
			this.loginFailedEventSink.fire(new LoginFailedEvent(this));
		}
		this.reset();
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isLoginEnabled() {
		return this.loginEnabled;
	}

	public void setLoginEnabled(boolean loginPossible) {
		this.loginEnabled = loginPossible;
	}

	private void reset() {
		this.userId = "";
		this.password = "";
		this.loginEnabled = false;
		this.resetLoginViewEventSink.fire(new ResetLoginViewEvent(this));
	}

	private void updateLoginButtonState(String userId, String password) {
		boolean userIdHasMinLength = userId.length() >= this.model.getMinUserIdLength();
		boolean passwordHasMinLength = password.length() >= this.model.getMinPasswordLength();

		this.loginEnabled = userIdHasMinLength && passwordHasMinLength;

		this.loginEnabledChangedEventSink.fire(new LoginEnabledChangedEvent(this));
	}
}
