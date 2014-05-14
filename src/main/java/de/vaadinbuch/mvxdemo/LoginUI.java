package de.vaadinbuch.mvxdemo;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
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
import de.vaadinbuch.mvxdemo.login.event.LoginFailedEvent;
import de.vaadinbuch.mvxdemo.login.event.LoginSuccessEvent;
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
public class LoginUI extends UI {

	/**
	 * Der Schlüssel für das Session-Attribut, das die Benutzerkennung
	 * speichert.
	 */
	public static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	private final EventBus eventBus = new EventBus();

	@Override
	protected void init(VaadinRequest request) {
		LoginComponent loginComponent = this.createLoginComponent();
		this.eventBus.register(this);

		Component loginView = loginComponent.getViewAs(Component.class);

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(loginView);
		layout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);

		this.setContent(layout);
		this.setSizeFull();
	}

	@Subscribe
	public void onLoginSuccess(LoginSuccessEvent event) {
		this.getSession().setAttribute(LOGIN_USER_ID, event.getUserId());
		this.getPage().reload();
	}

	@Subscribe
	public void onLoginFailed(LoginFailedEvent event) {
		Notification.show("Anmeldung fehlgeschlagen!", Type.ERROR_MESSAGE);
	}

	private LoginComponent createLoginComponent() {
		LoginComponent loginComponent = new LoginComponentImpl(new LoginModelImpl(ServiceLocator.getInstance()
				.getService(UserService.class)), new VaadinLoginViewLogic(new VaadinLoginView(), this.eventBus),
				this.eventBus);

		return loginComponent;
	}
}
