package de.vaadinbuch.mvxdemo.profileeditor;

import de.vaadinbuch.mvxdemo.domain.User;

/**
 * Die Interfacedefinition des Modells der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public interface ProfileEditorModel {

	/**
	 * Wird geworfen, wenn das Speichern des Profils fehlschlägt.
	 * 
	 * @author Frank Hardy
	 */
	public class ProfileSaveFailedException extends Exception {

		private static final long serialVersionUID = 1L;

		public ProfileSaveFailedException(String message) {
			super(message);
		}

		public ProfileSaveFailedException(String message, Exception e) {
			super(message, e);
		}
	}

	/**
	 * Liefert das Domänenobjekt eines bestimmten Benutzers.
	 * 
	 * @param userId
	 *            die Kennung des Benutzers.
	 * 
	 * @return das Domänenobjekt oder <code>null</code>.
	 */
	User getUser(String userId);

	/**
	 * Speichert die Daten eines bestimmten Benutzers.
	 * 
	 * @param user
	 *            das Domänenobjekt, das die Daten des Benutzers enthält.
	 * 
	 * @throws ProfileSaveFailedException
	 *             wenn der Speichervorgang fehlschlägt.
	 */
	void saveUser(User user) throws ProfileSaveFailedException;
}
