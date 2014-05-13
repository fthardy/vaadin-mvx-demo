package de.vaadinbuch.mvxdemo;

import com.vaadin.server.DefaultUIProvider;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

/**
 * Diese {@link UIProvider} Implementierung prüft, ob ein Benutzerobjekt in der
 * Session eingetragen ist. Ist dies der Fall so ist {@link MainUI} die
 * zurückgelieferte UI-Klasse. Andernfalls wird {@link LoginUI} als UI-Klasse
 * zurückgegeben.
 * 
 * @author Frank Hardy
 */
public class LoginAwareUIProvider extends DefaultUIProvider {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return VaadinSession.getCurrent().getAttribute(LoginUI.LOGIN_USER_ID) == null ? LoginUI.class : MainUI.class;
	}
}
