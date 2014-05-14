package de.vaadinbuch.mvxdemo.login.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginPresentationModel;

/**
 * Signalisiert der Anmeldeoberfläche das sich der Freigabezustand für die
 * Anmeldeaktion geändert hat.
 * 
 * @author Frank Hardy
 */
public class LoginEnabledChangedEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * @param presentationModel
     *            das Presentationmodel, welche diesen Event erzeugt und
     *            versendet hat.
     */
    public LoginEnabledChangedEvent(LoginPresentationModel presentationModel) {
        super(presentationModel);
    }
}
