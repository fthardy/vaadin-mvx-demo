package de.vaadinbuch.mvxdemo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese UI repräsentiert die Anwendungsoberfläche, für einen angemeldeten
 * Benutzer.
 * 
 * @author Frank Hardy
 */
@Theme("mvx-demo")
@SuppressWarnings("serial")
public class MainUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		Button logoutBTN = new Button("Abmelden");
		logoutBTN.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				onLogoutButtonClick();
			}
		});

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
}
