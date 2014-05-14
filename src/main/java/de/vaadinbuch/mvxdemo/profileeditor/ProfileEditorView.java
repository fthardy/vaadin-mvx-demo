package de.vaadinbuch.mvxdemo.profileeditor;

import de.vaadinbuch.mvxdemo.ViewAccessor;
import de.vaadinbuch.mvxdemo.domain.User;

/**
 * Die Interfacedefinition für die View der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public interface ProfileEditorView extends ViewAccessor {

	/**
	 * @param user
	 *            das Domänenobjekt des Benutzers, der bearbeitet werden soll.
	 */
	void setUserToEdit(User user);
}
