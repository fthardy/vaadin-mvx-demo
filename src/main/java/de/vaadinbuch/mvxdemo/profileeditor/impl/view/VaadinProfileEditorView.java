package de.vaadinbuch.mvxdemo.profileeditor.impl.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * Die Implementierung der Humble View.
 * 
 * @author Frank Hardy
 */
public class VaadinProfileEditorView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public static final String PRIMARY_STYLE_NAME = "profile-editor";
	public static final String FORM_STYLE_NAME = PRIMARY_STYLE_NAME + "-form";
	public static final String BUTTONS_STYLE_NAME = PRIMARY_STYLE_NAME + "-buttons";

	final TextField firstNameField = new TextField("Vorname:");
	final TextField lastNameField = new TextField("Nachname:");
	final TextField emailAddressField = new TextField("eMail-Adresse:");
	final DateField dateOfBirthField = new DateField("Geburtsdatum:");
	final Button saveButton = new Button("Speichern");
	final Button closeButton = new Button("Schliessen");

	public VaadinProfileEditorView() {
		this.setPrimaryStyleName(PRIMARY_STYLE_NAME);
		this.buildUI();
		this.initUI();
	}

	public TextField getFirstNameField() {
		return this.firstNameField;
	}

	public TextField getLastNameField() {
		return this.lastNameField;
	}

	public TextField getEmaiAddressField() {
		return this.emailAddressField;
	}

	public DateField getDateOfBirth() {
		return this.dateOfBirthField;
	}

	public Button getSaveButton() {
		return this.saveButton;
	}

	public Button getCloseButton() {
		return this.closeButton;
	}

	private void buildUI() {
		FormLayout form = new FormLayout();
		form.setSizeUndefined();
		form.addStyleName(FORM_STYLE_NAME);
		form.setSpacing(true);
		form.addComponent(this.firstNameField);
		form.addComponent(this.lastNameField);
		form.addComponent(this.emailAddressField);
		form.addComponent(this.dateOfBirthField);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSizeUndefined();
		buttons.addStyleName(BUTTONS_STYLE_NAME);
		buttons.setSpacing(true);
		buttons.addComponent(this.saveButton);
		buttons.addComponent(this.closeButton);

		this.setSizeUndefined();
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponent(form);
		this.addComponent(buttons);
		this.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);
	}

	private void initUI() {
		this.firstNameField.setColumns(30);
		this.lastNameField.setColumns(30);
		this.emailAddressField.setColumns(30);
	}
}
