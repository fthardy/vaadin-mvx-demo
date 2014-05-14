package de.vaadinbuch.mvxdemo.profileeditor.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorComponent;

/**
 * Signalisiert einen Fehler bei der Speicherung eines Benutzers aus der
 * Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
public class SaveFailedEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private final Throwable cause;

    /**
     * Erzeugt eine neue Instanz dieses Event.
     * 
     * @param component
     *            die Profileditorkomponente, die dieses Event versendet.
     * @param cause
     *            das {@link Throwable} Objekt das den Fehler verursacht hat
     *            oder <code>null</code>.
     */
    public SaveFailedEvent(ProfileEditorComponent component, Throwable cause) {
        super(component);
        this.cause = cause;
    }

    /**
     * @return das {@link Throwable} Objekt das den Fehler verursacht hat oder
     *         <code>null</code>.
     */
    public Throwable getCause() {
        return this.cause;
    }
}
