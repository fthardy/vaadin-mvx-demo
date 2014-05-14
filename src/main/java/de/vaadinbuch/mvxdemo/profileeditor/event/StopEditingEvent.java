package de.vaadinbuch.mvxdemo.profileeditor.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorComponent;

/**
 * Signalisiert das Beenden des Bearbeitungsvorgangs aus Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class StopEditingEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * Erzeugt eine neue Instanz dieses Event.
     * 
     * @param component
     *            die Profileditorkomponente, die dieses Event versendet.
     */
    public StopEditingEvent(ProfileEditorComponent component) {
        super(component);
    }
}
