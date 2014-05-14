package de.vaadinbuch.mvxdemo;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.login.LoginView;
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;

/**
 * Diese UI repräsentiert die Anwendungsoberfläche für einen Benutzer, der nicht
 * angemeldet ist.
 * 
 * @author Frank Hardy
 */
@CDIUI
@Theme("mvx-demo")
@SuppressWarnings("serial")
public class LoginUI extends UI {

	@Inject
	private LoginView loginView;

	/**
	 * Der Schlüssel für das Session-Attribut, das die Benutzerkennung
	 * speichert.
	 */
	public static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	public void onLoginSuccess(@Observes LoginSuccessEvent event) {
		this.getSession().setAttribute(LOGIN_USER_ID, event.getUserId());
		this.getPage().reload();
	}

	public void onLoginFailed(@Observes LoginFailedEvent event) {
		Notification.show("Anmeldung fehlgeschlagen!", Type.ERROR_MESSAGE);
	}

	@Override
	protected void init(VaadinRequest request) {
		Component loginView = this.loginView.getViewAs(Component.class);
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(loginView);
		layout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);
		layout.setSizeFull();

		this.setContent(layout);
		this.setSizeFull();
	}
}
