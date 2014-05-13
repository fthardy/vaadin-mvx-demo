package de.vaadinbuch.mvxdemo.login.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.vaadinbuch.mvxdemo.AbstractPresenter;
import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.LoginView;

/**
 * Die Implementierung der Anmeldekomponente, die auch gleichzeitig den
 * Presenter der MVP-Triade repräsentiert.
 * 
 * @author Frank Hardy
 */
public class LoginComponentImpl extends AbstractPresenter<LoginModel, LoginView> implements LoginView.Presenter,
		LoginComponent {

	private List<LoginSuccessHandler> successHandlerList;
	private List<LoginFailedHandler> failedHandlerList;

	/**
	 * Erzeugt eine neue Instanz der Anmeldekomponente und registriert sich an
	 * der View.
	 * 
	 * @param model
	 *            das Model für der Komponente.
	 * @param view
	 *            die View der Komponente.
	 */
	public LoginComponentImpl(LoginModel model, LoginView view) {
		super(model, view);
		this.view.setPresenter(this);
	}

	@Override
	public void onUserIdChange(String currentUid) {
		this.updateLoginButtonState(currentUid, this.view.getPassword());
	}

	@Override
	public void onPasswordChange(String currentPw) {
		this.updateLoginButtonState(this.view.getUserId(), currentPw);
	}

	@Override
	public void onLogin() {
		if (this.model.loginUser(this.view.getUserId(), this.view.getPassword())) {
			this.informSuccessHandler(this.view.getUserId());
		} else {
			this.informFailedHandler();
		}
		this.view.reset();
	}

	@Override
	public void addLoginSuccessHandler(LoginSuccessHandler handler) {
		this.successHandlerList = this.addHandlerToList(handler, this.successHandlerList);
	}

	@Override
	public void addLoginFailedHandler(LoginFailedHandler handler) {
		this.failedHandlerList = this.addHandlerToList(handler, this.failedHandlerList);
	}

	private void updateLoginButtonState(String userId, String password) {
		boolean userIdHasMinLength = userId.length() >= this.model.getMinUserIdLength();
		boolean passwordHasMinLength = password.length() >= this.model.getMinPasswordLength();

		this.view.setLoginButtonEnabled(userIdHasMinLength && passwordHasMinLength);
	}

	private <T> List<T> addHandlerToList(T handler, List<T> handlerList) {
		if (handlerList == null) {
			handlerList = Collections.synchronizedList(new ArrayList<T>());
		}
		if (!handlerList.contains(handler)) {
			handlerList.add(handler);
		}
		return handlerList;
	}

	private void informSuccessHandler(String userId) {
		List<LoginSuccessHandler> handlerList;
		if (this.successHandlerList != null) {
			synchronized (this.successHandlerList) {
				handlerList = new ArrayList<LoginSuccessHandler>(this.successHandlerList);
			}
			for (LoginSuccessHandler handler : handlerList) {
				handler.onLoginSuccess(userId);
			}
		}
	}

	private void informFailedHandler() {
		List<LoginFailedHandler> handlerList;
		if (this.failedHandlerList != null) {
			synchronized (this.failedHandlerList) {
				handlerList = new ArrayList<LoginFailedHandler>(this.failedHandlerList);
			}
			for (LoginFailedHandler handler : handlerList) {
				handler.onLoginFailed();
			}
		}
	}
}
