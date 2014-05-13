package de.vaadinbuch.mvxdemo.login.impl.view;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.login.LoginView;

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

	private LoginView.Presenter presenter;

	/**
	 * Erzeugt eine neuen Instanz dieser Logik.
	 * 
	 * @param view
	 *            die Instanz der Oberfläche.
	 */
	public VaadinLoginViewLogic(VaadinLoginView view) {
		if (view == null) {
			throw new NullPointerException("Undefined view!");
		}
		this.view = view;

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

	@Override
	public void setPresenter(LoginView.Presenter presenter) {
		this.presenter = presenter;
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
				onUserIdFieldChanges(event);
			}
		});
		this.view.getPasswordField().addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				onPasswordFieldChanges(event);
			}
		});
		this.view.getLoginButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				onLoginButtonClicked(event);
			}
		});
	}

	private void onUserIdFieldChanges(TextChangeEvent event) {
		this.presenter.onUserIdChange(event.getText());
	}

	private void onPasswordFieldChanges(TextChangeEvent event) {
		this.presenter.onPasswordChange(event.getText());
	}

	private void onLoginButtonClicked(ClickEvent event) {
		this.presenter.onLogin();
	}
}
