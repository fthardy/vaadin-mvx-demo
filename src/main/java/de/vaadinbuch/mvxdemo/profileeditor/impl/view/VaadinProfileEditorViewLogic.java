package de.vaadinbuch.mvxdemo.profileeditor.impl.view;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.vaadinbuch.mvxdemo.UnsupportedViewTypeException;
import de.vaadinbuch.mvxdemo.domain.User;
import de.vaadinbuch.mvxdemo.profileeditor.ProfileEditorView;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.CancelEditingEvent;
import de.vaadinbuch.mvxdemo.profileeditor.impl.event.SaveUserEvent;

/**
 * Die Implementierung der View des Profileditors.
 * 
 * @author Frank Hardy
 */
public class VaadinProfileEditorViewLogic implements ProfileEditorView {

	private final VaadinProfileEditorView view;
	private final UserHolder userHolder = new UserHolder();
	private final FieldGroup fieldBinding;

	@Inject
	Event<SaveUserEvent> saveUserEvent;
	@Inject
	Event<CancelEditingEvent> cancelEditingEvent;

	/**
	 * Erzeugt eine neue Instanz dieser Logik.
	 * 
	 * @param view
	 *            die Instanz der Humble View.
	 */
	@Inject
	public VaadinProfileEditorViewLogic(VaadinProfileEditorView view) {
		this(view, new FieldGroup());
	}

	/**
	 * Erzeugt eine neue Instanz dieser Logik.<br/>
	 * Dieser Konstruktor dient in erster Linie zum Testen.
	 * 
	 * @param view
	 *            die Instanz der Humble View.
	 * @param fieldGroup
	 *            eine {@link FieldGroup} Instanz.
	 */
	VaadinProfileEditorViewLogic(VaadinProfileEditorView view, FieldGroup fieldGroup) {
		this.view = view;
		this.fieldBinding = fieldGroup;
		this.buildFieldBinding();
		this.registerListeners();
	}

	@Override
	public void setUserToEdit(User user) {
		this.userHolder.setUser(user);
		this.fieldBinding.discard();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der übergebene Viewtyp wird nicht unterstützt: " + type.getName());
	}

	private void buildFieldBinding() {
		this.fieldBinding.setItemDataSource(new BeanItem<UserHolder>(this.userHolder));
		this.fieldBinding.bind(this.view.getFirstNameField(), "firstName");
		this.fieldBinding.bind(this.view.getLastNameField(), "lastName");
		this.fieldBinding.bind(this.view.getEmaiAddressField(), "emailAddress");
		this.fieldBinding.bind(this.view.getDateOfBirth(), "dateOfBirth");
	}

	@SuppressWarnings("serial")
	private void registerListeners() {
		this.view.getSaveButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				onSaveButtonClicked();
			}
		});
		this.view.getCloseButton().addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				onCancelButtonClicked();
			}
		});
	}

	private void onSaveButtonClicked() {
		try {
			this.fieldBinding.commit();
			this.saveUserEvent.fire(new SaveUserEvent(this, userHolder.getUser()));
		} catch (CommitException e) {
			Notification.show("Daten konnten nicht übertragen werden!", e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

	private void onCancelButtonClicked() {
		this.fieldBinding.discard();
		this.cancelEditingEvent.fire(new CancelEditingEvent(this));
	}
}
