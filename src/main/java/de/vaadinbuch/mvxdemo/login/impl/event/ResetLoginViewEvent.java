package de.vaadinbuch.mvxdemo.login.impl.event;

import java.util.EventObject;

import de.vaadinbuch.mvxdemo.login.LoginPresentationModel;

/**
 * Signalisiert der Anmeldeoberfläche, dass diese zurückgesetzt werden soll.
 * 
 * @author Frank Hardy
 */
public class ResetLoginViewEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * @param presentationModel
     *            das Presentationmodel, welche diesen Event erzeugt und
     *            versendet hat.
     */
    public ResetLoginViewEvent(LoginPresentationModel presentationModel) {
        super(presentationModel);
    }
}
