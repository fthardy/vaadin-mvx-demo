package de.vaadinbuch.mvxdemo.profileeditor.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorComponent;

/**
 * Signalisiert die erfolgreiche Speicherung eines Benutzers aus der
 * Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class SaveSuccessEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private final String userId;

    /**
     * Erzeugt eine neue Instanz dieses Event.
     * 
     * @param component
     *            die Profileditorkomponente, die dieses Event versendet.
     * @param userId
     *            die Kennung des gespeicherten Benutzers.
     */
    public SaveSuccessEvent(ProfileEditorComponent component, String userId) {
        super(component);
        this.userId = userId;
    }

    /**
     * @return die Kennung des gespeicherten Benutzers.
     */
    public String getUserId() {
        return this.userId;
    }
}
