package de.vaadinbuch.mvxdemo.profileeditor.impl.model;

import javax.inject.Inject;

import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.domain.UserService;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel;

/**
 * Die Implementierung des Models der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class ProfileEditorModelImpl implements ProfileEditorModel {

    private final UserService userService;

    /**
     * Erzeugt eine Instanz eines Profileditors.
     * 
     * @param service
     *            der UserService.
     */
    @Inject
    public ProfileEditorModelImpl(UserService service) {
        this.userService = service;
    }

    @Override
    public User getUser(String userId) {
        return this.userService.getUser(userId);
    }

    @Override
    public void saveUser(User user) throws ProfileSaveFailedException {
        try {
            this.userService.storeUser(user);
        } catch (Exception e) {
            throw new ProfileSaveFailedException(
                    "Speichern des Benutzers " + user.getUserId() + " ist fehlgeschlagen!", e);
        }
    }
}
