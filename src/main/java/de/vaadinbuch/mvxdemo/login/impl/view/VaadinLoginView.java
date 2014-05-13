package de.vaadinbuch.mvxdemo.login.impl.view;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.vaadinbuch.mvxdemo.login.LoginView;

/**
 * Die Implementierung der View für die Anmeldekomponente.
 * 
 * @author Frank Hardy
 */
public class VaadinLoginView extends VerticalLayout implements LoginView {

	private static final long serialVersionUID = 1L;

	final TextField userIdTF = new TextField("Benutzerkennung:");
	final PasswordField passwordField = new PasswordField("Passwort:");
	final Button loginBTN = new Button("Anmelden");

	LoginView.Presenter presenter;

	public VaadinLoginView() {
		this.setSizeUndefined();
		this.setMargin(true);
		this.setSpacing(true);
		this.buildUI();
		this.initUI();
	}

	@Override
	public String getUserId() {
		return this.userIdTF.getValue();
	}

	@Override
	public String getPassword() {
		return this.passwordField.getValue();
	}

	@Override
	public void setLoginButtonEnabled(boolean enabled) {
		this.loginBTN.setEnabled(enabled);
	}

	@Override
	public void reset() {
		this.userIdTF.setValue("");
		this.userIdTF.focus();
		this.passwordField.setValue("");
		this.loginBTN.setEnabled(false);
	}

	@Override
	public void setPresenter(LoginView.Presenter presenter) {
		this.presenter = presenter;
	}

	private void buildUI() {
		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();
		loginForm.setSpacing(true);
		loginForm.addComponent(this.userIdTF);
		loginForm.addComponent(this.passwordField);

		this.addComponent(loginForm);
		this.addComponent(this.loginBTN);
		this.setComponentAlignment(this.loginBTN, Alignment.MIDDLE_RIGHT);
	}

	@SuppressWarnings("serial")
	private void initUI() {
		this.userIdTF.setColumns(20);
		this.userIdTF.setImmediate(true);
		this.userIdTF.setRequired(true);
		this.userIdTF.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				onUserIdChange(event);
			}
		});
		this.passwordField.setColumns(20);
		this.passwordField.setImmediate(true);
		this.passwordField.setRequired(true);
		this.passwordField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				onPasswordChange(event);
			}
		});
		this.loginBTN.setImmediate(true);
		this.loginBTN.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				onLoginButtonClicked();
			}
		});
		this.reset();
	}

	void onUserIdChange(TextChangeEvent event) {
		if (this.presenter != null) {
			this.presenter.onUserIdChange(event.getText());
		}
	}

	void onPasswordChange(TextChangeEvent event) {
		if (this.presenter != null) {
			this.presenter.onPasswordChange(event.getText());
		}
	}

	void onLoginButtonClicked() {
		if (this.presenter != null) {
			this.presenter.onLogin();
		}
	}
}
