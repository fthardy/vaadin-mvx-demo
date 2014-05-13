package de.vaadinbuch.mvxdemo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.domain.ServiceLocator;
import de.vaadinbuch.mvxdemo.domain.UserService;
import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginComponent.LoginFailedHandler;
import de.vaadinbuch.mvxdemo.login.LoginComponent.LoginSuccessHandler;
import de.vaadinbuch.mvxdemo.login.impl.LoginComponentImpl;
import de.vaadinbuch.mvxdemo.login.impl.model.LoginModelImpl;
import de.vaadinbuch.mvxdemo.login.impl.view.VaadinLoginView;
import de.vaadinbuch.mvxdemo.login.impl.view.VaadinLoginViewLogic;

/**
 * Diese UI repräsentiert die Anwendungsoberfläche für einen Benutzer, der nicht
 * angemeldet ist.
 * 
 * @author Frank Hardy
 */
@Theme("mvx-demo")
@SuppressWarnings("serial")
public class LoginUI extends UI implements LoginSuccessHandler, LoginFailedHandler {

	/**
	 * Der Schlüssel für das Session-Attribut, das die Benutzerkennung
	 * speichert.
	 */
	public static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	@Override
	protected void init(VaadinRequest request) {
		LoginComponent loginComponent = this.createLoginComponent();
		loginComponent.addLoginSuccessHandler(this);
		loginComponent.addLoginFailedHandler(this);

		Component loginView = loginComponent.getViewAs(Component.class);

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(loginView);
		layout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);

		this.setContent(layout);
		this.setSizeFull();
	}

	@Override
	public void onLoginSuccess(String userId) {
		this.getSession().setAttribute(LOGIN_USER_ID, userId);
		this.getPage().reload();
	}

	@Override
	public void onLoginFailed() {
		Notification.show("Anmeldung fehlgeschlagen!", Type.ERROR_MESSAGE);
	}

	private LoginComponent createLoginComponent() {
		LoginComponent loginComponent = new LoginComponentImpl(new LoginModelImpl(ServiceLocator.getInstance()
				.getService(UserService.class)), new VaadinLoginViewLogic(new VaadinLoginView()));

		return loginComponent;
	}
}
