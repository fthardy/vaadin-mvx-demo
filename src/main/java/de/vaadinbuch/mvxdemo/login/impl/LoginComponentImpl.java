package de.vaadinbuch.mvxdemo.login.impl;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;

import de.vaadinbuch.mvxdemo.AbstractPresenter;
import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginModel;
import de.vaadinbuch.mvxdemo.login.LoginView;
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginAttemptEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.PasswordChangeEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.UserIdChangeEvent;

/**
 * Die Implementierung der Anmeldekomponente, die auch gleichzeitig den
 * Presenter der MVP-Triade repräsentiert.
 * 
 * @author Frank Hardy
 */
@UIScoped
public class LoginComponentImpl extends AbstractPresenter<LoginModel, LoginView> implements LoginComponent {

	@Inject
	Event<LoginSuccessEvent> loginSuccessEventSink;
	@Inject
	Event<LoginFailedEvent> loginFailedEventSink;

	/**
	 * Erzeugt eine neue Instanz der Anmeldekomponente.
	 * 
	 * @param model
	 *            das Model für der Komponente.
	 * @param view
	 *            die View der Komponente.
	 */
	@Inject
	public LoginComponentImpl(LoginModel model, LoginView view) {
		super(model, view);
	}

	public void onUserIdChange(@Observes UserIdChangeEvent event) {
		if (event.getSource() == this.view) {
			this.updateLoginButtonState(event.getCurrentUserId(), this.view.getPassword());
		}
	}

	public void onPasswordChange(@Observes PasswordChangeEvent event) {
		if (event.getSource() == this.view) {
			this.updateLoginButtonState(this.view.getUserId(), event.getCurrentPassword());
		}
	}

	public void onLogin(@Observes LoginAttemptEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.loginUser(this.view.getUserId(), this.view.getPassword())) {
				this.loginSuccessEventSink.fire(new LoginSuccessEvent(this, this.view.getUserId()));
			} else {
				this.loginFailedEventSink.fire(new LoginFailedEvent(this));
			}
			this.view.reset();
		}
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return this.view.getViewAs(type);
	}

	private void updateLoginButtonState(String userId, String password) {
		boolean userIdHasMinLength = userId.length() >= this.model.getMinUserIdLength();
		boolean passwordHasMinLength = password.length() >= this.model.getMinPasswordLength();

		this.view.setLoginButtonEnabled(userIdHasMinLength && passwordHasMinLength);
	}
}
