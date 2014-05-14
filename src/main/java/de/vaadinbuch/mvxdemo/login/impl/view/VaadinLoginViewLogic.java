package de.vaadinbuch.mvxdemo.login.impl.view;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.login.LoginComponent;
import de.vaadinbuch.mvxdemo.login.LoginPresentationModel;
import de.vaadinbuch.mvxdemo.login.LoginView;
import de.vaadinbuch.mvxdemo.login.impl.event.LoginEnabledChangedEvent;
import de.vaadinbuch.mvxdemo.login.impl.event.ResetLoginViewEvent;

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
@UIScoped
public class VaadinLoginViewLogic implements LoginView {

	private final VaadinLoginView view;
	private final LoginPresentationModel presentationModel;

	@SuppressWarnings("unused")
	private final FieldGroup fieldBinding;

	/**
	 * Erzeugt eine neuen Instanz dieser Logik.
	 * 
	 * @param view
	 *            die Instanz der Oberfläche.
	 */
	@Inject
	public VaadinLoginViewLogic(VaadinLoginView view, LoginPresentationModel presentationModel) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View!");
		}
		this.view = view;

		if (presentationModel == null) {
			throw new NullPointerException("Undefiniertes Presentationmodel!");
		}
		this.presentationModel = presentationModel;

		this.fieldBinding = this.createFieldBinding();

		this.registerViewListeners();

		this.reset();
	}

	@Override
	public LoginComponent getComponent() {
		return (LoginComponent) this.presentationModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (Component.class.isAssignableFrom(type) && type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der übergebene Viewtyp wird nicht unterstützt: " + type.getName());
	}

	public void onReset(@Observes ResetLoginViewEvent event) {
		if (this.presentationModel == event.getSource()) {
			this.reset();
		}
	}

	public void onLoginEnabledChanged(@Observes LoginEnabledChangedEvent event) {
		if (this.presentationModel == event.getSource()) {
			this.view.getLoginButton().setEnabled(this.presentationModel.isLoginEnabled());
		}
	}

	private FieldGroup createFieldBinding() {
		FieldGroup fieldBinding = new FieldGroup(new BeanItem<LoginPresentationModel>(this.presentationModel));
		// Die Werte werden direkt in das Presentationmodel geschrieben
		fieldBinding.setBuffered(false);
		fieldBinding.bind(this.view.getUserIdField(), "userId");
		fieldBinding.bind(this.view.getPasswordField(), "password");
		return fieldBinding;
	}

	private void reset() {
		this.view.getUserIdField().setValue(this.presentationModel.getUserId());
		this.view.getPasswordField().setValue(this.presentationModel.getPassword());
		this.view.getLoginButton().setEnabled(this.presentationModel.isLoginEnabled());
		this.view.getUserIdField().focus();
	}

	@SuppressWarnings("serial")
	private void registerViewListeners() {
		this.view.getUserIdField().addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				presentationModel.onUserIdChange(event.getText());
			}
		});
		this.view.getPasswordField().addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				presentationModel.onPasswordChange(event.getText());
			}
		});
		this.view.getLoginButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				presentationModel.onLogin();
			}
		});
	}
}
