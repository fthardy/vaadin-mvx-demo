package de.vaadinbuch.mvxdemo.profileeditor;

import de.vaadinbuch.mvxdemo.ViewAccessor;

/**
 * Die Definition der öffentlichen Schnittstelle der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public interface ProfileEditorComponent extends ViewAccessor {

	/**
	 * Definiert einen Benutzer, dessen Profil bearbeitet werden soll.
	 * 
	 * @param userId
	 *            die Kennung des Benutzers.
	 */
	void editProfileUser(String userId);
}