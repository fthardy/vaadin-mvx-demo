package de.vaadinbuch.mvxdemo.login.impl.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Die Implementierung der reinen Oberfläche für die Anmeldekomponente.<br/>
 * Sie beinhaltet ausschließlich den Aufbau der Oberfläche und die
 * Grundkonfiguration der einzelnen Oberflächenelemente. Des Weiteren bietet sie
 * Zugriff auf alle Oberflächenelemente, die von der Logik benötigt werden. Die
 * Logik der Oberfläche ist in {@link VaadinLoginViewLogic} implementiert.
 * 
 * @author Frank Hardy
 * 
 * @see VaadinLoginViewLogic
 */
public class VaadinLoginView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final TextField userIdField = new TextField("Benutzerkennung:");
	private final PasswordField passwordField = new PasswordField("Passwort:");
	private final Button loginButton = new Button("Anmelden");

	public VaadinLoginView() {
		this.setSizeUndefined();
		this.setSizeUndefined();
		this.setMargin(true);
		this.setSpacing(true);
		this.buildUI();
		this.initUI();
	}

	public TextField getUserIdField() {
		return this.userIdField;
	}

	public PasswordField getPasswordField() {
		return this.passwordField;
	}

	public Button getLoginButton() {
		return this.loginButton;
	}

	private void buildUI() {
		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();
		loginForm.setSpacing(true);
		loginForm.addComponent(this.userIdField);
		loginForm.addComponent(this.passwordField);

		this.addComponent(loginForm);
		this.addComponent(this.loginButton);
		this.setComponentAlignment(this.loginButton, Alignment.MIDDLE_RIGHT);
	}

	private void initUI() {
		this.userIdField.setColumns(20);
		this.userIdField.setImmediate(true);
		this.userIdField.setRequired(true);

		this.passwordField.setColumns(20);
		this.passwordField.setImmediate(true);
		this.passwordField.setRequired(true);

		this.loginButton.setImmediate(true);
	}
}
