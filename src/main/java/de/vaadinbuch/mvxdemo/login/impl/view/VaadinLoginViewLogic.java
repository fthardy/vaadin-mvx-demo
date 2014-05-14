package de.vaadinbuch.mvxdemo.login.impl.view;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.login.LoginView;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginAttemptEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.PasswordChangeEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.UserIdChangeEvent;

/**
 * Die Implementierung der Oberflächenlogik.<br/>
 * Gegenüber dem Presenter repräsentiert diese Klasse die View, daher wird hier
 * das Interface {@link LoginView} implementiert. Sie enthält nur die Logik der
 * Oberfläche der Anmeldekomponente. Die Oberfläche selbst wird von
 * {@link VaadinLoginView} separat implementiert und von dieser Klasse
 * referenziert.
 * 
 * @author Frank Hardy
 * 
 * @see VaadinLoginView
 */
public class VaadinLoginViewLogic implements LoginView {

	private final VaadinLoginView view;
	private final EventBus eventBus;

	/**
	 * Erzeugt eine neuen Instanz dieser Logik.
	 * 
	 * @param view
	 *            die Instanz der Oberfläche.
	 * @param eventBus
	 *            der Eventbus.
	 */
	public VaadinLoginViewLogic(VaadinLoginView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View!");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;

		this.registerViewListeners();

		this.reset();
	}

	@Override
	public String getUserId() {
		return this.view.getUserIdField().getValue();
	}

	@Override
	public String getPassword() {
		return this.view.getPasswordField().getValue();
	}

	@Override
	public void setLoginButtonEnabled(boolean enabled) {
		this.view.getLoginButton().setEnabled(enabled);
	}

	@Override
	public void reset() {
		this.view.getUserIdField().setValue("");
		this.view.getUserIdField().focus();
		this.view.getPasswordField().setValue("");
		this.view.getLoginButton().setEnabled(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der übergebene Viewtyp wird nicht unterstützt: " + type.getName());
	}

	@SuppressWarnings("serial")
	private void registerViewListeners() {
		this.view.getUserIdField().addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				eventBus.post(new UserIdChangeEvent(VaadinLoginViewLogic.this, event.getText()));
			}
		});
		this.view.getPasswordField().addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				eventBus.post(new PasswordChangeEvent(VaadinLoginViewLogic.this, event.getText()));
			}
		});
		this.view.getLoginButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				eventBus.post(new LoginAttemptEvent(VaadinLoginViewLogic.this));
			}
		});
	}
}
