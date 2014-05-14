package de.vaadinbuch.mvxdemo.profileeditor.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorView;

/**
 * Signalisiert das Beenden des Bearbeitungsvorgangs von der View an den
 * Presenter der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class CancelEditingEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * Erzeugt eine neue Instanz dieses Event.
     * 
     * @param view
     *            die View der Profileditorkomponente, die dieses Event
     *            versendet.
     */
    public CancelEditingEvent(ProfileEditorView view) {
        super(view);
    }
}
