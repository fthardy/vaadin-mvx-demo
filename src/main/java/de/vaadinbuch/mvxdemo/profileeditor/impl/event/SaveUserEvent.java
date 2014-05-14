package de.vaadinbuch.mvxdemo.profileeditor.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorView;

/**
 * Signalisiert das Beenden des Bearbeitungsvorgangs von der View an den
 * Presenter der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class SaveUserEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private final User user;

    /**
     * Erzeugt eine neue Instanz dieses Event.
     * 
     * @param view
     *            die View der Profileditorkomponente, die dieses Event
     *            versendet.
     * @param user
     *            der Benutzer, der gespeichert werden soll.
     */
    public SaveUserEvent(ProfileEditorView view, User user) {
        super(view);
        this.user = user;
    }

    /**
     * @return der Benutzer, der gespeichert werden soll.
     */
    public User getUser() {
        return this.user;
    }
}
