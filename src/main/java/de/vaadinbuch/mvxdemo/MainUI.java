package de.vaadinbuch.mvxdemo;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorComponent;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveFailedEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveSuccessEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.StopEditingEvent;

/**
 * Diese UI repräsentiert die Anwendungsoberfläche, für einen angemeldeten
 * Benutzer.
 * 
 * @author Frank Hardy
 */
@CDIUI(value = "main")
@Theme("mvx-demo")
@SuppressWarnings("serial")
public class MainUI extends UI {

	private Window window = new Window("Profil bearbeiten");

	@Inject
	private ProfileEditorComponent profileEditor;

	public void onSaveSuccessful(@Observes SaveSuccessEvent event) {
		Notification.show("Profildaten wurden für Benutzer " + event.getUserId() + " gespeichert.",
				Type.TRAY_NOTIFICATION);
	}

	public void onSaveFailed(@Observes SaveFailedEvent event) {
		Notification.show("Profildaten konnten nicht gespeichert werden!", event.getCause().getMessage(),
				Type.ERROR_MESSAGE);
	}

	public void onStopEditing(@Observes StopEditingEvent event) {
		this.window.close();
	}

	@Override
	protected void init(VaadinRequest request) {
		Button logoutBTN = new Button("Abmelden");
		logoutBTN.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				onLogoutButtonClick();
			}
		});

		Button profileEditBTN = new Button("Bearbeite Profil");
		profileEditBTN.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				onEditUserProfile();
			}
		});

		HorizontalLayout leftBar = new HorizontalLayout();
		leftBar.setSpacing(true);
		leftBar.setSizeUndefined();
		leftBar.addComponent(profileEditBTN);
		leftBar.setComponentAlignment(profileEditBTN, Alignment.MIDDLE_LEFT);

		HorizontalLayout rightBar = new HorizontalLayout();
		rightBar.setSpacing(true);
		rightBar.setSizeUndefined();
		rightBar.addComponent(logoutBTN);
		rightBar.setComponentAlignment(logoutBTN, Alignment.MIDDLE_RIGHT);

		HorizontalLayout headBar = new HorizontalLayout();
		headBar.setSizeFull();
		headBar.setHeight(null);
		headBar.setMargin(true);
		headBar.setSpacing(true);
		headBar.addComponent(leftBar);
		headBar.setComponentAlignment(leftBar, Alignment.MIDDLE_LEFT);
		headBar.addComponent(rightBar);
		headBar.setComponentAlignment(rightBar, Alignment.MIDDLE_RIGHT);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponent(headBar);

		this.setContent(mainLayout);
		this.setSizeFull();
	}

	private void onLogoutButtonClick() {
		this.getSession().setAttribute(LoginUI.LOGIN_USER_ID, null);
		this.getPage().reload();
	}

	private void onEditUserProfile() {
		this.profileEditor.editProfileUser(this.getSession().getAttribute(LoginUI.LOGIN_USER_ID).toString());

		this.window.setSizeUndefined();
		this.window.setContent(this.profileEditor.getViewAs(Component.class));
		this.window.setClosable(false);
		this.window.setResizable(false);
		this.window.setWindowMode(WindowMode.NORMAL);
		this.window.setModal(true);
		this.window.center();

		this.addWindow(this.window);
	}
}
