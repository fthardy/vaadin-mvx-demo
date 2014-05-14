package de.vaadinbuch.mvxdemo.profileeditor.impl;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;

import de.vaadinbuch.mvxdemo.AbstractPresenter;
import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorComponent;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorModel.ProfileSaveFailedException;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorView;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveFailedEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.SaveSuccessEvent;
import de.vaadinbuch.mvxdemo.profileeditor.event.StopEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.CancelEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.SaveUserEvent;

/**
 * Die Implementierung der Profileditorkomponente.
 * 
 * @author Frank Hardy
 */
@UIScoped
public class ProfileEditorComponentImpl extends AbstractPresenter<ProfileEditorModel, ProfileEditorView> implements
		ProfileEditorComponent {

	@Inject
	Event<SaveSuccessEvent> saveSuccessEvent;
	@Inject
	Event<SaveFailedEvent> saveFailedEvent;
	@Inject
	Event<StopEditingEvent> stopEditingEvent;

	@Inject
	public ProfileEditorComponentImpl(ProfileEditorModel model, ProfileEditorView view) {
		super(model, view);
	}

	@Override
	public void editProfileUser(String userId) {
		this.view.setUserToEdit(this.model.getUser(userId));
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return this.view.getViewAs(type);
	}

	public void saveUser(@Observes SaveUserEvent event) {
		if (event.getSource() == this.view) {
			try {
				this.model.saveUser(event.getUser());
				this.saveSuccessEvent.fire(new SaveSuccessEvent(this, event.getUser().getUserId()));
			} catch (ProfileSaveFailedException e) {
				this.saveFailedEvent.fire(new SaveFailedEvent(this, e));
			}
		}
	}

	public void cancelEditing(@Observes CancelEditingEvent event) {
		if (event.getSource() == this.view) {
			this.stopEditingEvent.fire(new StopEditingEvent(this));
		}
	}
}
